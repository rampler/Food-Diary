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
    /// Provides REST methods for Meal.
    /// </summary>
    public interface IMealAPI {

        Guid Create(string name, DateTime consuptionDate, Guid userId);
        Guid Get(Guid userId);
        IEnumerable<DB.Model.Meal> List();
        bool Update(Guid id, string name, DateTime consuptionDate, Guid userId);
        bool Delete(Guid id);

    }
}

/* by Bartłomiej Hebda | 2014 */