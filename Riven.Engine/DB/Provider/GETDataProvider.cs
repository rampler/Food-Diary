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
using System.IO;
using System.Linq.Expressions;
using System.Net;
using System.Text;

namespace Riven.Engine.DB.Provider {

    /// <summary>
    /// Provides Request-Response communication beetwen Riven and Baron-Server
    /// </summary>
    public class GETDataProvider : IDataProvider {

        private WebRequest WebRequest;

        /// <summary>
        /// Receives data from Baron-Server using GET method. Example usage: 
        /// Request(new Uri("http://a.pl/create/getId"), "login", "riven"));
        /// </summary>
        public string Request(Uri localization, params string[] args) {
            try {
                var path = localization.ToString() + FormatArgs(args);
                Logger.Info("Trying to GET '{0}' from server.", path);

                WebRequest = WebRequest.Create(path);

                using (var stream = WebRequest.GetResponse().GetResponseStream()) {
                    using (var reader = new StreamReader(stream)) {
                        var response = reader.ReadToEnd();
                        Logger.Info("Response has been received!");
                        Logger.Debug(response);
                        return response;
                    }
                }
            } catch (Exception e) {
                Logger.Error("An error with Request-Response communication has occured. It's probably connection issue");
                Logger.Error("More info: {0}", e.ToString());
                return string.Empty;
            }
        }

        /// <summary>
        /// FormatArgs is able to transform args from string array to "GET" form. Example usage:
        /// FormatArgs("login", "riven", "password", "bunny") -> "?login=riven&password=bunny"
        /// </summary>
        private string FormatArgs(params string[] args) {
            try {
                var query = new StringBuilder();

                query.Append("?");
                for (int i = 0; i < args.Length; i++) {
                    query.Append(args[i]);
                    query.Append(i % 2 == 0 ? "=" : "&");
                }

                query.Remove(query.Length - 1, 1);
                return query.ToString();
            } catch (Exception e) {
                Logger.Error("Problem with tranformation from string array to GET query");
                Logger.Error("More info: {0}", e.ToString());
                return string.Empty;
            }
        }

        private NLog.Logger Logger = NLog.LogManager.GetCurrentClassLogger();
    }
}

/* by Bartłomiej Hebda | 2014 */