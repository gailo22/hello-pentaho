<html>
<body>
<h2> Pentaho Reporting</h2>
 
<a onclick="openPopup()" href="#">generate pdf</a>	


<script>

	var reportLocation = "http://localhost:8080/pentaho/api/repos/:public:Reporting:13_Adding_%20Subreports.prpt/report?output-target=pageable%2Fpdf&accepted-page=-1&showParameters=true";

	function openPopup() {
		window.location = reportLocation;
		window.open('', 'popup');
	}	
	
</script>

</body>
</html>