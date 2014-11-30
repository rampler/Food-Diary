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
    /// Provides REST methods for product.
    /// </summary>
    public interface IProductAPI {

        Guid Create(DB.Model.Product product);
        IEnumerable<DB.Model.Product> List();
        IEnumerable<DB.Model.ProductCategory> Categories();
        bool Update(Guid id, DB.Model.Product product);
        bool Delete(Guid id);

    }
}

/* by Bartłomiej Hebda | 2014 */