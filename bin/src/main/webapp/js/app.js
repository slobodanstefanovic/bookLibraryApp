function loadContent(){
  fragmentId = location.hash.substring(1) + '.html';
  var objectTag = document.getElementById("open-html-page");
  if (objectTag != null) {
        objectTag.setAttribute('data', fragmentId);
  }
}

if(!location.hash) {
  location.hash = "#home";
}

loadContent();

window.addEventListener("hashchange", loadContent)