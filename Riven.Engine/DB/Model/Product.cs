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

namespace Riven.Engine.DB.Model {

    /// <summary>
    /// Representation of Product.
    /// </summary>
    public class Product {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public int Calories { get; set; }
        public int Carbon { get; set; }
        public int Protein { get; set; }
        public int Fat { get; set; }
        public ProductCategory Category { get; set; }
    }

}

/* by Bartłomiej Hebda | 2014 */