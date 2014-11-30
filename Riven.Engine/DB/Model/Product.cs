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
        public double Calories { get; set; }
        public double Carbon { get; set; }
        public double Protein { get; set; }
        public double Fat { get; set; }
        public ProductCategory Category { get; set; }

        public override string ToString() {
            return string.Format("[PRODUCT: {0}; {1}]", Name, Category.ToString());
        }

    }

}

/* by Bartłomiej Hebda | 2014 */