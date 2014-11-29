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

        public Guid Create(string login, string password) {
            var localization = new Uri(ServerAddress, "/user/create");
            Logger.Info("Trying to create new user id from '{0}'", login, localization.ToString());

            var response = Provider.Request(localization, "login", login, "password", password);
            Logger.Info("Response: " + response);

            if (response == string.Empty) {
                Logger.Error("User could not be created!", login);
                return Guid.Empty;
            }

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        public Guid GetId(string login) {
            var localization = new Uri(ServerAddress, "/user/getId");
            Logger.Info("Trying to get user id for '{0}' from '{1}'", login, localization.ToString());

            var response = Provider.Request(localization, "login", login);
            Logger.Info("Response: " + response);

            if (response == string.Empty) {
                Logger.Warn("User '{0}' does not exists!", login);
                return Guid.Empty;
            }

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        public bool Delete(Guid id) {
            var localization = new Uri(ServerAddress, "/user/delete");
            Logger.Info("Trying to delete user with id = '{0}' using '{1}'", id, localization.ToString());

            var response = Provider.Request(localization, "id", id.ToString());
            Logger.Info("Response: " + response);

            if (response == string.Empty) {
                Logger.Warn("User '{0}' does not exists!", id);
                return false;
            }

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

    }
}

/* by Bartłomiej Hebda | 2014 */