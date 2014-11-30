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
using System.Globalization;

namespace Riven.Engine.API.Support {

    /// <summary>
    /// Supports API for profile.
    /// </summary>
    public class Profile : IProfileAPI {

        private Uri ServerAddress;
        private IDataProvider Provider;
        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();

        public Profile(Uri serverAddress, IDataProvider provider) {
            ServerAddress = serverAddress;
            Provider = provider;
        }

        /// <summary>
        /// /profile/create?first_name=abc&last_name=yyy&weight=12&calories_counter=123&age=21&user_id=6b7649ae-2958-4fbe-81c0-8ba0006b7f53
        /// </summary>
        public Guid Create(DB.Model.Profile profile, Guid userId) {
            Logger.Info("Creating new profile: {0}", profile.ToString());

            var localization = new Uri(ServerAddress, "/profile/create");
            var response = Provider.Request(localization,
                "first_name", profile.FirstName, "last_name", profile.LastName,
                "weight", profile.Weight.ToString(CultureInfo.InvariantCulture),
                "calories_counter", profile.CaloriesCounter.ToString(CultureInfo.InvariantCulture),
                "age", profile.Age.ToString(), "user_id", userId.ToString());

            if (response == string.Empty) {
                Logger.Error("Profile could not be created!");
                return Guid.Empty;
            }

            Logger.Debug("Server resonse: " + response);

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        /// <summary>
        /// /profile/list
        /// </summary>
        public IEnumerable<DB.Model.Profile> List() {
            Logger.Info("Trying to get all profiles from server");

            var profiles = new LinkedList<DB.Model.Profile>();
            var localization = new Uri(ServerAddress, "/profile/list");
            var response = Provider.Request(localization);

            if (response == string.Empty) {
                Logger.Warn("There is no registered profiles yet!");
                return profiles;
            }

            Logger.Info("Server response: " + response);

            JArray obj = JArray.Parse(response);

            foreach (var child in obj.Children()) {
                DB.Model.Profile current = new DB.Model.Profile();
                current.Id = Guid.Parse((string)child["id"]);
                current.FirstName = (string)(child["firstName"]);
                current.LastName = (string)(child["lastName"]);
                current.Age = int.Parse((string)(child["age"]));
                current.CaloriesCounter = double.Parse((string)child["caloriesCounter"], CultureInfo.InvariantCulture);
                current.Weight = double.Parse((string)child["weight"], CultureInfo.InvariantCulture);
                profiles.AddLast(current);
            }

            return profiles;
        }

        /// <summary>
        /// /profile/update?id={guid}|&PROFILE
        /// </summary>
        public bool Update(Guid id, DB.Model.Profile profile, Guid userId) {
            Logger.Info("Updating profile: {0}", profile.ToString());

            var localization = new Uri(ServerAddress, "/profile/update");
            var response = Provider.Request(localization,"id", id.ToString(),
                "first_name", profile.FirstName, "last_name", profile.LastName,
                "weight", profile.Weight.ToString(CultureInfo.InvariantCulture),
                "calories_counter", profile.CaloriesCounter.ToString(CultureInfo.InvariantCulture),
                "age", profile.Age.ToString(), "user_id", userId.ToString());

            if (response == string.Empty) {
                Logger.Warn("Profile '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

        /// <summary>
        /// /profile/delete?id={guid}
        /// </summary>
        public bool Delete(Guid id) {
            Logger.Info("Deleting profile with id: {0}", id.ToString());

            var localization = new Uri(ServerAddress, "/profile/delete");
            var response = Provider.Request(localization, "id", id.ToString());

            if (response == string.Empty) {
                Logger.Warn("Profile '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

        /// <summary>
        /// /profile/isUserHave?id={guid}"
        /// </summary>
        public bool IsUserHave(Guid userId) {
            Logger.Info("Checking if user has profile with user_id: {0}", userId.ToString());

            var localization = new Uri(ServerAddress, "/profile/isUserHave");
            var response = Provider.Request(localization, "user_id", userId.ToString());

            if (response == string.Empty) {
                Logger.Warn("Profile for userId = '{0}' does not exists!", userId.ToString());
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }
    }
}

/* by Bartłomiej Hebda | 2014 */