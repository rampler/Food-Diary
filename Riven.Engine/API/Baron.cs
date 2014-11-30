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
            Meal = new Support.Meal(serverAddress, provider);
        }

        public Riven.Engine.API.Support.User User { get; private set; }
        public Riven.Engine.API.Support.Profile Profile { get; private set; }
        public Riven.Engine.API.Support.Product Product { get; private set; }
        public Riven.Engine.API.Support.Meal Meal { get; private set; }

        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();

        public static void Main(string[] args) {
            var Baron = new Baron(new Uri("http://foodiary.ddns.net:8080/"), new GETDataProvider());

            foreach (var x in Baron.Meal.List())
                Console.WriteLine(x.Name + " >> " + x.ConsumptionDay + " >> " + x.User.ToString());
            Guid meal = Baron.Meal.Create("xxxxx", DateTime.Now, Guid.Parse("6b7649ae-2958-4fbe-81c0-8ba0006b7f53"));

                foreach (var x in Baron.Meal.List())
                Console.WriteLine(x.Name + " >> " + x.ConsumptionDay + " >> " + x.User.ToString());

            Baron.Meal.Delete(meal);
            Console.ReadLine();
        }

    }
}

/* by Bartłomiej Hebda | 2014 */
