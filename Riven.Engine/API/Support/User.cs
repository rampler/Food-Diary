/*
 
                     /   ))     |\         )               ).
               c--. (\  ( `.    / )  (\   ( `.     ).     ( (           
               | |   ))  ) )   ( (   `.`.  ) )    ( (      ) )          
               | |  ( ( / _..----.._  ) | ( ( _..----.._  ( (           
 ,-.           | |---) V.'-------.. `-. )-/.-' ..------ `--) \._        
 | /===========| |  (   |      ) ( ``-.`\/'.-''           (   ) ``-._   
 | | / / / / / | |--------------------->  <-------------------------_>=-
 | \===========| |                 ..-'./\.`-..                _,,-'    
 `-'           | |-------._------''_.-'----`-._``------_.-----'         
               | |         ``----''            ``----''                  
               | |                                                       
               c--`   RIVEN... A Sword Mirrors Its Owner
*/

using System;
using Riven.Engine.DB.Provider;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;

namespace Riven.Engine.API.Support {

    /// <summary>
    /// Supports API for user.
    /// </summary>
    public class User : IUserAPI {

        private Uri ServerAddress;
        private IDataProvider Provider;
        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();

        public User(Uri serverAddress, IDataProvider provider) {
            ServerAddress = serverAddress;
            Provider = provider;
        }

        /// <summary>
        /// /user/create?login=abc&password=yyy
        /// </summary>
        public Guid Create(DB.Model.User user) {
            Logger.Info("Creating new user: {0}", user.ToString());

            var localization = new Uri(ServerAddress, "/user/create");
            var response = Provider.Request(localization, 
                "login", user.Login, "password", user.Password);

            if (response == string.Empty) {
                Logger.Error("User could not be created!");
                return Guid.Empty;
            }

            Logger.Debug("Server resonse: " + response);

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        /// <summary>
        /// /user/getId?login=abc
        /// </summary>
        public Guid GetId(string login) {
            Logger.Info("Getting id for user with login = {0}", login);

            var localization = new Uri(ServerAddress, "/user/getId");
            var response = Provider.Request(localization, "login", login);

            if (response == string.Empty) {
                Logger.Warn("User '{0}' does not exists!", login);
                return Guid.Empty;
            }

            Logger.Debug("Server resonse: " + response);

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        /// <summary>
        /// /user/list
        /// </summary>
        public IEnumerable<DB.Model.User> List() {
            Logger.Info("Trying to get all users from server");

            var users = new LinkedList<DB.Model.User>();
            var localization = new Uri(ServerAddress, "/user/list");
            var response = Provider.Request(localization);

            if (response == string.Empty) {
                Logger.Warn("Nobody registered yet!");
                return users;
            }
            
            Logger.Info("Server response: " + response);

            JArray obj = JArray.Parse(response);

            foreach (var child in obj.Children()) {
                DB.Model.User current = new DB.Model.User();
                current.Id = Guid.Parse((string)child["id"]);
                current.Login = (string)(child["login"]);
                current.Password = (string)(child["password"]);
                users.AddLast(current);
            }

            return users;
        }

        /// <summary>
        /// /user/update?id={guid}|&login=xxx|&password=yyy
        /// </summary>
        public bool Update(Guid id, DB.Model.User user) {
            Logger.Info("Updating user: {0}", user.ToString());

            var localization = new Uri(ServerAddress, "/user/update");
            var response = Provider.Request(localization, "id", id.ToString(), 
                "login", user.Login, "password", user.Password);

            if (response == string.Empty) {
                Logger.Warn("User '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

        /// <summary>
        /// /user/delete?id={guid}
        /// </summary>
        public bool Delete(Guid id) {
            Logger.Info("Deleting user with id: {0}", id.ToString());

            var localization = new Uri(ServerAddress, "/user/delete");
            var response = Provider.Request(localization, "id", id.ToString());

            if (response == string.Empty) {
                Logger.Warn("User '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

        

    }
}

/* by Bartłomiej Hebda | 2014 */