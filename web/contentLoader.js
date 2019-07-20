//a function for clearing the content area
function clearContentArea(){
  $("#contentArea").html("");
}
//a function to load a json file
function loadJSON(filename){
  //fetch the json for the given file
  var filepath = filename + ".json";
  fetch(filepath)
    //then respond
  .then( function(response){
  return response.json();})
    //then call slidebuilder using myJSON
  .then (function(myJson){
    //access json here
  });
}










