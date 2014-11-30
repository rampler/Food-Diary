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
    public class Meal : IMealAPI {

        private Uri ServerAddress;
        private IDataProvider Provider;
        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();

        public Meal(Uri serverAddress, IDataProvider provider) {
            ServerAddress = serverAddress;
            Provider = provider;
        }

        /// <summary>
        /// /meal/create
        /// </summary>
        public Guid Create(string name, DateTime consuptionDate, Guid userId) {
            Logger.Info("Creating new meal '{0}' for user: ", name, userId);

            var localization = new Uri(ServerAddress, "/meal/create");
            var response = Provider.Request(localization,
                "name", name, "consumption_date", consuptionDate.ToLongDateString(), "user_id", userId.ToString()
            );

            if (response == string.Empty) {
                Logger.Error("Meal could not be created!");
                return Guid.Empty;
            }

            Logger.Debug("Server resonse: " + response);

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        public Guid Get(Guid userId) {
            throw new NotImplementedException();
        }

        /// <summary>
        /// /meal/list
        /// </summary>
        public IEnumerable<DB.Model.Meal> List() {
            Logger.Info("Trying to get all meals from server");

            var meals = new LinkedList<DB.Model.Meal>();
            var localization = new Uri(ServerAddress, "/meal/list");
            var response = Provider.Request(localization);

            if (response == string.Empty) {
                Logger.Warn("There is no registered meals yet!");
                return meals;
            }

            Logger.Info("Server response: " + response);

            JArray obj = JArray.Parse(response);

            foreach (var child in obj.Children()) {
                DB.Model.Meal current = new DB.Model.Meal();
                current.Id = Guid.Parse((string)child["id"]);
                current.Name = (string)(child["name"]);
                current.ConsumptionDay = DateTime.Parse((string)(child["consumptionDay"]));
                current.User = Guid.Parse((string)child["user"]);
                meals.AddLast(current);
            }

            return meals;
        }

        /// <summary>
        /// /meal/update
        /// </summary>
        public bool Update(Guid id, string name, DateTime consuptionDate, Guid userId) {
            Logger.Info("Updating meal: {0}", id.ToString());

            var localization = new Uri(ServerAddress, "/meal/update");
            var response = Provider.Request(localization,
                "name", name, "consumption_date", consuptionDate.ToShortDateString(), "user_id", userId.ToString()
            );

            if (response == string.Empty) {
                Logger.Warn("Meal '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

        /// <summary>
        /// /meal/delete?id={guid}
        /// </summary>
        public bool Delete(Guid id) {
            Logger.Info("Deleting meal with id: {0}", id.ToString());

            var localization = new Uri(ServerAddress, "/meal/delete");
            var response = Provider.Request(localization, "id", id.ToString());

            if (response == string.Empty) {
                Logger.Warn("Meal '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }
    }
}

/* by Bartłomiej Hebda | 2014 */