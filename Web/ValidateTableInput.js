/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function validateTableInput()
{
var x=document.forms["tableForm"]["tbl"].value


if (x==null || x=="")
  {
    alert("Επιλέξτε πίνακα");
    return false;
  }
  
/*var from=document.forms["tabeForm]"] ["tblfrom"].value;
var to=document.forms["tableForm]"] ["tblto"].value;*/

from= document.forms["tableForm"]["tblfrom"].value
to= document.forms["tableForm"]["tblto"].value
//alert("Nothing")
if (((from == null) || (from == "")) && ((to == null) || (to == ""))) {
  alert("Δέν μπορεί νά είναι καί το \"Από\" καί τό \"Σέ\" κενά");
  return false;
}

return true;
}
