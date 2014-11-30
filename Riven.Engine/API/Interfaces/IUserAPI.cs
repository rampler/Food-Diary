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
using System.Collections.Generic;

namespace Riven.Engine.API {

    /// <summary>
    /// Provides REST methods for user.
    /// </summary>
    public interface IUserAPI {

        Guid Create(DB.Model.User user);
        Guid GetId(string login);
        IEnumerable<DB.Model.User> List();
        bool Update(Guid id, DB.Model.User user);
        bool Delete(Guid id);

    }
}

/* by Bartłomiej Hebda | 2014 */