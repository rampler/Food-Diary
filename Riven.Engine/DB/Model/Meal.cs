﻿/*
 
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

namespace Riven.Engine.DB.Model {

    /// <summary>
    /// Representation of Meal.
    /// </summary>
    public class Meal {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public DateTime ConsumptionDay { get; set; }
        public Guid User { get; set; }

        public override string ToString() {
            return string.Format("[MEAL: {0}; {1}]", Name, ConsumptionDay);
        }

    }

}

/* by Bartłomiej Hebda | 2014 */