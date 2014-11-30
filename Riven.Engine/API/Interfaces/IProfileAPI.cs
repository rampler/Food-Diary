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
    /// Provides REST methods for Profile.
    /// </summary>
    public interface IProfileAPI {

        Guid Create(string firstName, string lastName, double weight, double caloriesCounter, int age, Guid userId);
        IEnumerable<DB.Model.Profile> List();
        bool Update(Guid id, string firstName, string lastName, double weight, double caloriesCounter, int age, Guid userId);
        bool Delete(Guid id);
        bool IsUserHave(Guid id);

    }
}

/* by Bartłomiej Hebda | 2014 */