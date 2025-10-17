var request = null;

//request 생성 function Definition	 	 	
function createRequest(){	 	
	try{
		request = new XMLHttpRequest();
		alert ("XMLHttpRequest() 로 request instancet생성완료");
		}catch(trymicrosoft){
		try{
			request = new ActiveXObject("Msxml2.XMLHTTP");
			alert ("ActiveXObject(Msxml2.XMLHTTP) 로 request instancet생성완료");
		}catch(othermicrosoft){
			try{
				request =new ActiveXObject("Microsoft.XMLHTTP");
				alert ("new ActiveXObject(Microsoft.XMLHTTP) 로 request instancet생성완료");
			}catch(failed){
				request = null;
			}
		}
	}
	if( request == null ){
		alert ("request객체 생성시 error 발생함.");
	}
} 