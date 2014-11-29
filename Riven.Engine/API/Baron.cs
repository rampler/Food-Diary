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
        }

        public Riven.Engine.API.Support.User User { get; private set; }

        public static void Main(string[] args) {
            var provider = new GETDataProvider();
            var uri = new Uri("http://foodiary.ddns.net:8080/");
            var baron = new Baron(uri, provider);

            Guid refId = baron.User.Create("bartqh", "abcd");
            Guid curId = baron.User.GetId("bartqh");
            if (refId == curId)
                Console.WriteLine("OK");
            Console.WriteLine(baron.User.Delete(refId));
            Console.WriteLine("done!");
            Console.ReadLine();

        }

        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();
    }
}

/* by Bartłomiej Hebda | 2014 */
