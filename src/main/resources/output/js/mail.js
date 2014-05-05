function valider() {

	var form_err = " " ;
	var courriel = document.getElementById('courriel');
	if ( courriel.email.value.length < 1) {
		form_err = "Email invalide ! - ";
	}
	if ( courriel.email.value == "votrenom@mail.com") {
		form_err = "Email invalide ! - ";
	}

	var verim = 0;
	for (i=1; i<courriel.email.value.length -4; i++) {
		if ( courriel.email.value.charAt(i) == "@") {
			verim = 1;
		}
	}
	if ( verim == 0) {
		form_err = "Email invalide ! - ";
	}	
	if ( courriel.Nom.value.length < 1) {
		form_err += "Il manque le Nom. - ";
	}
	if ( courriel.Prenom.value.length < 1) {
		form_err += "Il manque le Prenom. ";
	}
	if ( form_err != " ") {
		alert(form_err);
		return false;
	}
	
	MaIlMe=new Array();
	MaIlMe[0]="151156146157100146157156144141164151157";
	MaIlMe[1]="156055144157165170143150141155160163056";
	MaIlMe[2]="156145164";
	OutString="";
	for(i=0;i<MaIlMe.length;i++){
		for(j=0;j<MaIlMe[i].length;j+=3){
			OutString+=eval("\"\\"+MaIlMe[i].slice(j,j+3)+"\"");
		}
	}
	
	
	document.getElementById('courriel').action ="mailto:" + unescape(OutString);
	confirmation();
	
	return true;
}

// Fonction de validation du formulaire
function validation() {
	document.getElementById('courriel').submit();
	}

// Fonction affichant la page de confirmation
function confirmation() {
	location.href = "confirmation.html";
	}
 
