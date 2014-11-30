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
using System.Net;
using Riven.Engine.DB.Provider;

namespace Riven.Engine.API {

    /// <summary>
    /// Contains all supported parts of REST methods from Baron.
    /// </summary>
    public class Baron {
        
        public Baron(Uri serverAddress, IDataProvider provider) {
            User = new Support.User(serverAddress, provider);
            Profile = new Support.Profile(serverAddress, provider);
            Product = new Support.Product(serverAddress, provider);
        }

        public Riven.Engine.API.Support.User User { get; private set; }
        public Riven.Engine.API.Support.Profile Profile { get; private set; }
        public Riven.Engine.API.Support.Product Product { get; private set; }

        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();

        public static void Main(string[] args) {
            var Baron = new Baron(new Uri("http://foodiary.ddns.net:8080/"), new GETDataProvider());

            foreach (var x in Baron.Product.List()) {
                Console.WriteLine(x.Name + " >> " + x.Category);
            }

            foreach (var x in Baron.Product.Categories()) {
                Console.WriteLine(x.ToString());
            }


            Console.ReadLine();

            Guid productid = Baron.Product.Create(new DB.Model.Product() { Name = "xxx", Category = DB.Model.ProductCategory.MEAT, Protein = 1.2, Carbon = 3.2, Fat = 2.1, Calories = 231 });
            foreach (var x in Baron.Product.List()) {
                Console.WriteLine(x.Name + " >> " + x.Category);
            }

            foreach (var x in Baron.Product.Categories()) {
                Console.WriteLine(x.ToString());
            }


            Console.ReadLine();
            Baron.Product.Delete(productid);
        }

    }
}

/* by Bartłomiej Hebda | 2014 */
