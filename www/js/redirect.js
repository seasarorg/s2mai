var lang = '?';

if (window.navigator.language){
  lang = window.navigator.language.toLowerCase() ;
} else if (window.navigator.userLanguage){
  lang = window.navigator.userLanguage.toLowerCase() ;
}

if(lang != "ja") {
	location.href='en/index.html';
} else {
	location.href='ja/index.html';
}
