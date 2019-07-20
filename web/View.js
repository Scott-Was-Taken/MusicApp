var usersName=getUsername();
const user = {
    firstName: usersName,
};
//build a greeting element for the user
const element = (
    <h4>
    Hello, {usersName}!
    </h4>
);
//render the user's greeting
ReactDOM.render(
    element,
    document.getElementById('greeting')
);




//a function to get the user's information
function getUsername(){
    
    var usersname=document.getElementById("usersname").value;
    
    // Return username
    return usersname;
}


//function to render a table of singers 
function buildSingerTable(){
    const singerTable=(
        <table style={{width:'100%'}} className={"pure-table"}>
            <thead>
            <tr>
                <th>Singer</th>
                <th>DOB</th>
                <th>Sex</th>
            </tr>

            </thead>
            <tbody id={"singerTable"}>

            </tbody>


        </table>
        
    );

    //render the table
    ReactDOM.render(
        singerTable,
        document.getElementById("contentArea")
    );
    //clear the table of rows
    $("#singerTable tr").remove(); 

    //grab the table
    var table=document.getElementById("singerTable");
    //create a row element
    var row=document.createElement('tr');
    row.innerHTML="";
    row.classList.add("row");
    var singerJson=getJson("singerJson");
    singerJson.forEach(function(element) {
            table.appendChild(makeItATableRow(element.name,element.dob,element.sex));
    });

    
}

//a function to return json objects from the hidden fields given the id of the box
function getJson(id){
    var Json=document.getElementById(id).innerHTML
    
    //singerJson = singerJson.replace("[", "");
    //singerJson = singerJson.replace("]", "");
    //Json=Json.slice(1,-1);
    var Json=JSON.parse(Json);
    console.log("Got JSON from "+id);
    console.log(Json);
    return Json;
}

//function to turn the name, code and track into table rows and return a row element
function makeItATableRow(name,dob,sex){
    //create a row element
    var row=document.createElement('tr');
    //create vars for the cells
    var nameCell=document.createElement('td');
    var dobCell=document.createElement('td');
    var sexCell=document.createElement('td');
    
    
    nameCell.innerHTML=name;
    dobCell.innerHTML=dob;
    sexCell.innerHTML=sex;
    //append all this data to the row
    row.appendChild(nameCell);
    row.appendChild(dobCell);
    row.appendChild(sexCell);
    //on row click load the singers albums
    row.onclick=function(){loadSingersAlbumsTable(nameCell.innerHTML)};
    return row;
}

buildAlbumsTable();
function displaySingers(){
    if ($('#singerTable').length > 0) {
        //do nothing, the table is already there.
    }
    else
    {
        buildSingerTable();
    }

}

function displayAlbums(){
    if ($('#albumsTable').length > 0) {
        //do nothing, the table is already there.
    }
    else
    {
        buildAlbumsTable();
    }

}

//function to render a table of albums
function buildAlbumsTable(){
    
    const albumTable=(
        <table style={{width:'100%'}} className={"pure-table"}>
            <thead>
            <tr>
                <th>Album</th>
                <th>Singer</th>
                <th>Year</th>
                <th>Company</th>
            </tr>

            </thead>
            <tbody id={"albumTable"}>

            </tbody>


        </table>
    );

    //render the table
    ReactDOM.render(
        albumTable,
        document.getElementById("contentArea")
    );
    //clear the table of all rows
    $("#albumTable tr").remove(); 
    //grab the table
    var table=document.getElementById("albumTable");
    //create a row element
    var row=document.createElement('tr');
    row.innerHTML="";
    row.classList.add("row");
    var albumJson=getJson("albumJson");
    //we need to also get the singer JSON so that we can identify singers by ID
    var singerJson=getJson("singerJson");
    var singer;
    
    albumJson.forEach(function(element) {           
        var i=0;
        //for each singer
        for (i = 0; i < singerJson.length; i++) { 
            //if the singer id on the singer and the album match
            if (singerJson[i].ng_singers_id==element.ng_singers_id){
                //set singer to the name of the matching singer
                singer=singerJson[i].name;
                //our record is fully ready to be appended to the table now that we know the singer
                table.appendChild(makeItAlbumTableRow(element.album_name,singer,element.release_year,element.record_company));
            }
        }
    });

    
}



//function to turn the album details into table rows and return a row element
function makeItAlbumTableRow(singer,name,year,company){
    //create a row element
    var row=document.createElement('tr');
    //create vars for the cells
    var singerCell=document.createElement('td');
    var nameCell=document.createElement('td');
    var yearCell=document.createElement('td');
    var companyCell=document.createElement('td');
    
    
    singerCell.innerHTML=singer;
    nameCell.innerHTML=name;
    yearCell.innerHTML=year;
    companyCell.innerHTML=company;
    //append all this data to the row
    row.appendChild(singerCell);
    row.appendChild(nameCell);
    row.appendChild(yearCell);
    row.appendChild(companyCell)
    return row;
}


//a function that takes in a singers name and loads only thier albums
function loadSingersAlbumsTable(name){
    const albumTable=(
        <table style={{width:'100%'}} className={"pure-table"}>
            <thead>
            <tr>
                <th>Album</th>
                <th>Singer</th>
                <th>Year</th>
                <th>Company</th>
            </tr>

            </thead>
            <tbody id={"albumTable"}>

            </tbody>


        </table>
    );

    //render the table
    ReactDOM.render(
        albumTable,
        document.getElementById("contentArea")
    );
    //clear the table of all rows
    $("#albumTable tr").remove(); 
    //grab the table
    var table=document.getElementById("albumTable");
    //create a row element
    var row=document.createElement('tr');
    row.innerHTML="";
    row.classList.add("row");
    var albumJson=getJson("albumJson");
    //we need to also get the singer JSON so that we can identify singers by ID
    var singerJson=getJson("singerJson");
    var singer;
    
    albumJson.forEach(function(element) {           
        var i=0;
        //for each singer
        for (i = 0; i < singerJson.length; i++) { 
            //if the singer id on the singer and the album match
            if (singerJson[i].ng_singers_id==element.ng_singers_id){
                //set singer to the name of the matching singer
                singer=singerJson[i].name;
                //our record is fully ready to be appended to the table now that we know the singer
                if(singer==name){
                    table.appendChild(makeItAlbumTableRow(element.album_name,singer,element.release_year,element.record_company));
                }
            }
        }
    });


    
}

//a function to display the header of a search 
function displaySearchTitle(){
    var search;
    var searchType;
    var searchGreeting;
    //first check for singers
    if ( $( "#singersearch" ).length ) {
        search=document.getElementById("singersearch").value;
        searchType="singer";
    }
    //now check album
    if ( $( "#albumsearch" ).length ) {
        search=document.getElementById("albumsearch").value;
        searchType="album";
    }
    console.log(search);
    //if there is somewhere to put it, drop a message on the header about what we are searching
    if ( $( "#searchgreeting" ).length ) {
        searchGreeting=document.getElementById("searchgreeting");
        searchGreeting.innerHTML=("Searching for '"+search+ "' in "+ searchType+ "s");
    }
    
    
    
}
displaySearchTitle();
