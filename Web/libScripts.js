
function validateAddTableInput() {
	
	var tableentry=document.forms["tblAdd"]["tblSelect"].value;
	var tablevalue=document.forms["tblAdd"]["tblNewRec"].value;
	
	if ((tableentry == null) || (tableentry == "")) {
		alert("Επιλέξτε πίνακα");
		return false;
	}
	
	if ((tablevalue == null) || (tablevalue == "")) {
		alert("Εισάγετε όνομα στοιχείου");
		return false;
	}
	
	return true;

}
function validateDelTableInput() {
	
	var tabelentry = document.forms["tableDelete"]["tblfield"].value;
	
	if ((tabelentry == null) || (tabelentry == "")) {
		alert("Επιλέξτε πίνακα");
		return false;
	}
	
	//document.forms["tableDelete"].action = '/Library/DelTableRec?tabelentry=' + tabelentry;
	//document.forms["tableDelete"].submit();
	
	return true;
}

function validateDeleteTableInput() {
	
	var selectedtable=document.forms["tableDeleteTableRec"]["tblSelect"].value;

	if (selectedtable==null || selectedtable=="") {
		alert("Επιλέξτε πίνακα");
		return false;
	}

	return true;

}

function validateRenameTableInput() {
	
	var selectedtable=document.forms["tableRename"]["tbl"].value;

	if (selectedtable==null || selectedtable=="") {
		alert("Επιλέξτε πίνακα");
		return false;
	}

	from= document.forms["tableRename"]["tblOpt"].value;
	to= document.forms["tableRename"]["tblto"].value;
	
	//Both 'from' and 'to' fields are empty
	if (((from == null) || (from == "")) && ((to == null) || (to == ""))) {
		alert("Εισάγετε πεδία \"Από\" καί \"Σέ\" γιά μετατροπή");
		return false;
	}

	// Field 'from' is the only one supplied.
		if ((from == "") && (to != "")) {
		r= confirm("Όλες οί κενές θέσεις τού πίνακα '" + selectedtable + "' θά ανικατασταθούν μέ τό '" + from + "'" + " πού έχεται εισάγει. Οί αλλαγές θά είναι μή αναστρέψιμες. Είστε βέβαιοι γιά τίς αλλαγές αυτές;");
		if (r == true)
			return true;
		else
			return false;
	}
	
	// Field 'to' is the only one supplied.
		
	if ((from != "") && (to == "")) {
	r= confirm("Σβήσιμο στοιχείου '" + from + "' από όλες τίς εγγραφές πού τό περιέχουν;");
	
	if (r == true)
		return true;
	else
		return false;
	}

	return true;

}

function validateAddRecordInput(btn, mode, cursor) {
		
	//s = "validateAddRecordInput";
	//alert(s);
	if (btn == 'Προηγούμενο') {
		document.forms["addRecordForm"]["buttonpressed"].value = 'previous';
		document.forms["addRecordForm"].action = '/Library/BrowseUpdateDB?mode=' + mode + "&cursor=" + cursor;
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (btn == 'Επόμενο') {
		document.forms["addRecordForm"].action = '/Library/BrowseUpdateDB?mode=' + mode + "&cursor=" + cursor;
		document.forms["addRecordForm"]["buttonpressed"].value = 'after';
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (btn == 'Διαγραφή') {
		document.forms["addRecordForm"].action = '/Library/InputEditDelete?mode=edit&cursor=' + cursor;
		document.forms["addRecordForm"]["buttonpressed"].value = 'delete';
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	// Προσθήκες
		
	if (btn == 'Προσθήκη/Επεξεργασία')
		document.forms["addRecordForm"].action = "/Library/BrowseUpdateDB?mode=add_while_editing";
	
	if (btn == 'Προσθήκη')
		document.forms["addRecordForm"].action = "/Library/BrowseUpdateDB?mode=add";
	
	if (document.forms["addRecordForm"]["code"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["author"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["title"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["category"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["subject_key_words"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["pub_ref"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["tod"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["notes"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["sector"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
	
	if (document.forms["addRecordForm"]["tod"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
		
	if (document.forms["addRecordForm"]["folder"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
			
	if (document.forms["addRecordForm"]["file_location"].value != "") {
		document.forms["addRecordForm"].submit();
		return (true);
	}
		
	alert("Κενή εγγραφή");
	
	return (false);
}

function selectedDropDowns(formname, sector, typeofdoc, category, folder) {

	if (sector != "null")
		document.getElementById('ddLSector').value = sector;
	
	if (typeofdoc != "null")
		document.getElementById('ddLTypeofDoc').value = typeofdoc;
	
	if (category != "null")
		document.getElementById('ddLCategory').value = category;
	
	if (folder != "null")
		document.getElementById('ddLFolder').value = folder;
	
  return true;
}

function fillTextAreas(formname, title, keywords, author, pub_ref, notes) {
  
	alert("TITLE: " + title);
	alert("AUTHOR: " + author);
	alert("NOTES: " + notes);
	document.forms[formname]["title"].value = title;
	document.forms[formname]["subject_key_words"].value = keywords;
	document.forms[formname]["author"].value = author;
	document.forms[formname]["pub_ref"].value = pub_ref;
	document.forms[formname]["notes"].value = notes;
  
	return true;
}

function deleteRecord(counter) {
	
	var r = confirm("Διαγραφή εγγραφής μέ κωδικό " + counter + ";");
	
	if (r == true)
		return true;
	else
		return false;
}

function DisplayWindow() {
	displayWindow = window.open("","LibWindow");
	
	return true;
}

function enableAddBtn(formname, enable) {
	
	if (enable == true)
		document.forms[formname]['aButton'].disabled = false;
	else if (enable == false)
		document.forms[formname]['aButton'].disabled = true;
}

function dispResultsButtonPressed(btn, pg) {
	
	//document.forms['searchresults'].action = '/Library/LibDisplaySearchResults.jsp?page=' + pg;
	//document.forms['searchresults'].submit();
	window.location.href = 'LibDisplaySearchResults.jsp?page=' + pg;
	
	return;
}

function Ttest(s) {
	document.forms["tableDelete"]["hiddentbl"].value = s;
	alert(s);
}

function validateTableInput() {
var x=document.forms["tableForm"]["tbl"].value;

if (x==null || x=="") {
    alert("Ξ•Ο€ΞΉΞ»Ξ­ΞΎΟ„Ξµ Ο€Ξ―Ξ½Ξ±ΞΊΞ±");
    return false;
}
  
/*var from=document.forms["tabeForm]"] ["tblfrom"].value;
var to=document.forms["tableForm]"] ["tblto"].value;*/

from= document.forms["tableForm"]["tblfrom"].value;
to= document.forms["tableForm"]["tblto"].value;
//alert("Nothing")
if (((from == null) || (from == "")) && ((to == null) || (to == ""))) {
  alert("Ξ”Ξ­Ξ½ ΞΌΟ€ΞΏΟ�ΞµΞ― Ξ½Ξ¬ ΞµΞ―Ξ½Ξ±ΞΉ ΞΊΞ±Ξ― Ο„ΞΏ \"Ξ‘Ο€Ο�\" ΞΊΞ±Ξ― Ο„Ο� \"Ξ£Ξ­\" ΞΊΞµΞ½Ξ¬");
  return false;
}

return true;
}

function validateCode() {
 var code=document.forms["addRecordForm"]["code"].value;
 
  if ((/[A-Za-z]/.test(code)) || (/[Α-Ωα-ω]/.test(code)))
    document.forms["addRecordForm"]["code"].value = code.toUpperCase();
  else
    document.forms["addRecordForm"]["code"].value = "";
  
  return true;
}


function renameElement(fname, tid, lblname) {
	
	var tname = document.getElementById(tid).value;
	//alert('fname: ' + fname + ' tid: ' + tid + ' tname: ' + tname);
	$.post('tables.jsp', {tablename: tname, lbl: lblname}, function(data) {
	//$.post('tables.jsp', $("#tableAdd").serialize(), function(data) {
		$.post('tables.jsp', {tablename: tname, lbl: lblname}, function(data) {
			$("#poptbl").html(data).show();
	}, "html");
}


function lookup(inputString) {
	
	if(inputString.length == 0) {
	$('#suggestions').hide();
	} else {
		$.get("authors.jsp", {queryString: ""+inputString+""}, function(data){
			if(data.length >0) {
				$('#suggestions').show();
				$('#autoSuggestionsList').html(data);
			}
		}
	);
}
}

function lookup_search(inputString) {
	
	if(inputString.length == 0) {
	$('#suggestionsSearch').hide();
	} else {
		$.post("authors.jsp", {queryString: ""+inputString+""}, function(data){
			if(data.length >0) {
				$('#suggestionsSearch').show();
				$('#autoSuggestionsSearchList').html(data);
			}
		}
	);
}
}

function fill(thisValue) {
	
	$('#inputString').val(thisValue);
	setTimeout("$('#suggestions').hide();", 200);
}
