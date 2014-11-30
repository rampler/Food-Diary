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
    /// Supports API for user.
    /// </summary>
    public class Product : IProductAPI {

        private Uri ServerAddress;
        private IDataProvider Provider;
        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();

        public Product(Uri serverAddress, IDataProvider provider) {
            ServerAddress = serverAddress;
            Provider = provider;
        }

        /// <summary>
        /// /product/create
        /// </summary>
        public Guid Create(DB.Model.Product product) {
            Logger.Info("Creating new product: {0}", product.ToString());

            var localization = new Uri(ServerAddress, "/product/create");
            var response = Provider.Request(localization,
                "name", product.Name, "calories", product.Calories.ToString(CultureInfo.InvariantCulture),
                "carbon", product.Carbon.ToString(CultureInfo.InvariantCulture), "protein", product.Protein.ToString(CultureInfo.InvariantCulture),
                "fat", product.Fat.ToString(CultureInfo.InvariantCulture), "category", product.Category.ToString()
            );

            if (response == string.Empty) {
                Logger.Error("Product could not be created!");
                return Guid.Empty;
            }

            Logger.Debug("Server resonse: " + response);

            JObject obj = JObject.Parse(response);
            return Guid.Parse((string)obj["id"]);
        }

        /// <summary>
        /// /product/list
        /// </summary>
        public IEnumerable<DB.Model.Product> List() {
            Logger.Info("Trying to get all products from server");

            var products = new LinkedList<DB.Model.Product>();
            var localization = new Uri(ServerAddress, "/product/list");
            var response = Provider.Request(localization);

            if (response == string.Empty) {
                Logger.Warn("There is no registered products yet!");
                return products;
            }

            Logger.Info("Server response: " + response);

            JArray obj = JArray.Parse(response);

            foreach (var child in obj.Children()) {
                DB.Model.Product current = new DB.Model.Product();
                current.Id = Guid.Parse((string)child["id"]);
                current.Name = (string)(child["name"]);
                current.Calories = double.Parse((string)(child["calories"]), CultureInfo.InvariantCulture);
                current.Carbon = double.Parse((string)(child["carbs"]), CultureInfo.InvariantCulture);
                current.Protein = double.Parse((string)child["protein"], CultureInfo.InvariantCulture);
                current.Fat = double.Parse((string)(child["fat"]), CultureInfo.InvariantCulture);
                current.Category = (DB.Model.ProductCategory)Enum.Parse(typeof(DB.Model.ProductCategory), (string)child["category"]);
                products.AddLast(current);
            }

            return products;
        }

        /// <summary>
        /// /product/categories
        /// </summary>
        public IEnumerable<DB.Model.ProductCategory> Categories() {
            Logger.Info("Trying to get all product categories from server");

            var categories = new LinkedList<DB.Model.ProductCategory>();
            var localization = new Uri(ServerAddress, "/product/categories");
            var response = Provider.Request(localization);

            if (response == string.Empty) {
                Logger.Warn("There is no registered categories yet!");
                return categories;
            }

            Logger.Info("Server response: " + response);

            JArray obj = JArray.Parse(response);

            foreach (var child in obj.Children()) {
                DB.Model.ProductCategory current = (DB.Model.ProductCategory)Enum.Parse(typeof(DB.Model.ProductCategory), (string)child);
                categories.AddLast(current);
            }

            return categories;
        }

        /// <summary>
        /// /product/update?id={guid}|&product|
        /// </summary>
        public bool Update(Guid id, DB.Model.Product product) {
            Logger.Info("Updating product: {0}", id.ToString());

            var localization = new Uri(ServerAddress, "/profile/update");
            var response = Provider.Request(localization,
               "name", product.Name, "calories", product.Calories.ToString(CultureInfo.InvariantCulture),
               "carbs", product.Calories.ToString(CultureInfo.InvariantCulture), "protein", product.Protein.ToString(CultureInfo.InvariantCulture),
               "fat", product.Fat.ToString(CultureInfo.InvariantCulture), "category", product.Category.ToString());

            if (response == string.Empty) {
                Logger.Warn("Profile '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }

        /// <summary>
        /// /product/delete
        /// </summary>
        public bool Delete(Guid id) {
            Logger.Info("Deleting category with id: {0}", id.ToString());

            var localization = new Uri(ServerAddress, "/product/delete");
            var response = Provider.Request(localization, "id", id.ToString());

            if (response == string.Empty) {
                Logger.Warn("Category '{0}' does not exists!", id);
                return false;
            }

            Logger.Info("Server response: " + response);

            JObject obj = JObject.Parse(response);
            return bool.Parse((string)obj["result"]);
        }
    }
}

/* by Bartłomiej Hebda | 2014 */