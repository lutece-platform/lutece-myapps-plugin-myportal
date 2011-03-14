--
-- Dumping data into table myportal_category
--
INSERT INTO myportal_category (id_category, name, description) VALUES 
(1,'Accessoires','Calculette, Convertiseur,...');
INSERT INTO myportal_category (id_category, name, description) VALUES 
(2,'Actualités internes','Vie des directions');
INSERT INTO myportal_category (id_category, name, description) VALUES 
(3,'Actualités externes','Le Monde, Le Parisien, ...');
INSERT INTO myportal_category (id_category, name, description) VALUES 
(4,'Restaurants administratifs','Menus des restaurants administratifs');

--
-- Dumping data into table myportal_widget_style
--
INSERT INTO myportal_widget_style (id_style, name, css_class) VALUES 
(0,'Défaut' , 'myportal-portlet');
INSERT INTO myportal_widget_style (id_style, name, css_class) VALUES 
(1,'Non repliable' , 'myportal-portlet-fixed' );

--
-- Dumping data into table myportal_user_pageconfig
--
INSERT INTO myportal_user_pageconfig (user_guid, user_pageconfig) VALUES 
('default','{\"page\":  { \"name\": \"Ma page\", \"tabs\":[{ \"name\": \"Tab 1\" , \"widgets\":[{ \"id\":4, \"state\":0, \"column\":1  }, { \"id\":1, \"state\":0, \"column\":1  },{ \"id\":6, \"state\":0, \"column\":3  }, { \"id\":2, \"state\":0, \"column\":2 },{ \"id\":3, \"state\":0, \"column\":3  },{ \"id\":5, \"state\":0, \"column\":2 } ] }] }}');

--
-- Dumping data into table myportal_widget
--
INSERT INTO myportal_widget VALUES 
(1,'Calculette','Calculatrice simple',1,'html',0x89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA20000001974455874536F6674776172650041646F626520496D616765526561647971C9653C000009B84944415478DA54577D6C5D6519FF9DEF733FDADBF6AE1F2B5DDBB55BC78650B601226E630C83A0C640C00F50084130C4C4841804F51F1305E40FA2D118634C50026410080E7566C6652C088CB10DB68E8D51BAAD6B3B58BF6ECBEDFD3A5FF71C7FCF7BCB8CB77D7B4EDF73DEF7797ECFF37B7ECF7BB52449B0EBAD9358DD91D30F9CFEF8F1BFBF73FA6EDDB49BAB61044B03F80BCD30209F8443E78879A7EE351D9A86FFFB68EA476E1A0F4C5E527A5CEF6871FE7ACFF62B1EB9A2BFBBA069063431BCFFE898F5F0CEFD87924CEB95564B0BB4B40DDB34B8B90E4BD760D2B0A6C9BDAE0CDBBC1AB2989B3A26E7743AA6C91C8738C4FF350EC368CC7BE52ACE8D7D84E2C2F9C21FEEBFE9C6ABD6F68D8861E3AEDFBCF2EEC87C349C1EB804473EBE0044316039B2835A28585DC3525771A0D5B490241A16FC0829CB4636E5226524B0F986B16C54A38B31DF252EA41D1BDBB877E1EC199C3AF56EF8CA2377F41ADDD77DEDC997472EDC9EBDB41FEF9E9D84E638309D144CD344936520CB6BAB6D236DE8C818263AB8498AD7949E60538B8D0E5A2B7B3E9A5D17AD7C66F399CB21D71411B73916328CF789E902D60CF4A35CAC19274F9FEED56E79ECC5DA949B734F54CAD06984F985F89B56613450ABEB28966A58914BA39B1BD7E318F37E8CB54D3A5EFFCE46C45C737C7C1A0FBD7E0125B3094D349210A9CE3CA4CD463AE24492A661CEAFE3AA8E1CAA67473C9DD13253DC943B42673825476946D7B14C4C2F85480755DCB7AE095AB586A972089BE867963C6CCE3BD0B3CD30DD3436ADEF436FD6C052102BA3422B5719D594514537998CEB08852FB6ABE94450B3851C86ADC891D61BBC2C793136B79AD8F3AD8D78FAAEED78F1D60DC8C5151C3DB7803E37C6C35BD6A8FCD5AA9E62EFEAB62614031AD22D2C453A26176B98AD4624A9A908E945754489E04B6404A641AF427187444AF10D312E8E2CCC15F1E84DC3181E5C05DF0FB0E3730338D899C3EE93D3B876751E3D5D79653451660D6CE9CBE1B74746513232D8DCE1E2AB9BDA70E87C11BB26CB18684B238C0539231247BC2660312470F4866193465DDD6C6C46A6EE1E9DC53736F6431E5719EACEF63CBEB73DAF4C5568545B2E578FE4BA717005F6DF99A8DC0E77E7A0A79B70DB5011E3AF8CE2E49209668645515746D1283943D5292B9A0E18CBE29060555B335E3BBB88B1A95958B6ADA4A14A63B51A431B85C8A41DA4D32C239B8E124D4C839BD7AEC2C6353D482C17954A0D46268707AE68C37CB1D8901FE626619EC5B4A9144985D754CC8B96EF9782048BB34B38B758C5DA5E59C33AB52C3A61A25E2D617CC6434042F6E45C34E7B2248EA68CE90C4F4C47329914E60A0B78F29D19AC6CC92AB4318D46F5500133E58FCB305B3418D26BBFE2C1D1EA589BB1F0EDAF5F866BD6AC543996BAA656E02F6F9CC00B63352C862C19BA9EE77B9F6F4BF0C8F50370C8F02008E1D8167CAF861FFEF3237C5075B021AF73DE879FC4F009CE6E48A9A65857E7DFA8EAE3D75B7BB075B003BDCD29584DCD88A3081E3793B03EF3C6497C7FDF0C7A3ABBD0EE525028300B750DBF3A368F26770A3FBA710322BE6BB214F78E9CC14BA7CBD8B66E15C2DA120D268AD584CD70C7E2B4304C27C5750C640CDCFBC5210C5ED2092B9D51F9F402CA221144B50A769EF6D0D3D1895E967DABAD3152095A4C8A425F07768F5731333B07374591A9D7B1A16705BEB4D2C0C133533857658809CCA893D18AD510C36C02147C485E8800A177B1D348AE54A3A1184C2ED4F029EBB43D65287190328C15AB35503971A618E2AD8962A3297801FA586EFFB8E76AFCFCEA360C6548A820A04391422BF5AFCB429D3ADCC50D67230B5F797E04CFBC7982B10F481057914A18EDD71BACCC925CC645456A0892C7165AE373AF1E371CD51A44733219FCF4964DD8F7DD6174A7629CAF86080826E2D00DAE94D6E7B0A49C5406872B161EDCF7316E7BEE30266616E1529F9968AC6A71B02263836244A3B1DA5C5B56A422E73A5DE0AA95D9FFF5653EAFD5FCC63F4E1616A3E9B3EBF971A360752C37F4885207B2791DF37C1D5BD8AB9309767F380B8361F529EED9E62CAECB2738767E1EE584F2CAEEC38A432936706462015BBB5D0CF57592CDBE0A83AA718A10638C02733F477DD768BC26AD5578A5BC93C64ED42EA7A49F2EF80906733A65B0458537224283A87EBC6D009639897F4D2CE2FD995085B78B7BDF7FA98D9F6CED252774C50B8B7AB8F7E414F68ECD60AC50C25429C04C62224D6CFA670222FC1156A7443535AA10DBE227D30B78EA0B6D18EEEF8247668B0C4A2D67C8F4476FDA80BBA7E7F0F6144B8439DDD495C1A5FD9D4A8B24AFC28B4F1716F1C0DF8E62A26AA1256B2B254B9B743EF4A9D9B122A5299B8A62A9E30AF33CF1A987FB8632B867EB0684248D78277D59676DFA64AB7497EEAE76DCDED571319F125E51248D5113D6BA7C77755B0E15D2A3891A2D7915A41EAF812016564B7E754A2496FB689A27908F8A7CA9BC4442F014916214E8E9E27C813D5A57A4A95268A4C605614281715C0719D6AF68B6EF8770293C43ED19CC57AA3444A41C21D31570C4714344948028D49A34080397642DCA9C8B87F68CE3D5F7CEE289D7C6F1C09E496C79F603BCF4DE241DB12FD6B8238C676DFEE7E828CBDF53FABC5CFAB863A895502B0A5D409D56486950502B0191A21086C60AB9A68EB4EBF2291C2D91306FCEE3B9D11A8E2C0017CC3C9E7F7F4E6DE6DA0609C4AE4612FD6CCF715CFFEC71DCF0E70338347A4EB1593EFF1E635763F482385446ABCB1ED5895AA4539A8429AC16CF5246E39CEC337CAD2E7B68BA157E182AF11864294CF3E85328D690EFC8F264011CF8601C4F1DBA80F5EBFB71A2B0849B5F3886C7B72FE1F84C117F7CBF80F67C13D52A8027270FB14B1E4887A2F888DF51AA54AA20CB3CE9EA60D63894EBECCFB59021AA27EADE4E22CC85094ECD95B1A5BD55B5B9DF1D9E82994E215BF7319073301DDAF8C1DEB3542483878626B6ED00356E28F5AE713DD8A16C56821DD735ED8997F7EFDC335AB9F3DAEDDBB0EFC373E8E2615ED07B147A755C49445B6355F482DED6EB58E1484B8C7062911B49AFE4BB652EF25503D0C87CC969A48C468A1011A272192B5776A2E5420137F466DED0CA9E97BDF9B19D73836B37BAB9BED578EBCC79A289199A86C20832A5D75C1C3222051E6D2B64B59C9DDAD98B8530253EF768CCA003BE62AF10096A1F092F88BCBFBB1383FC1270FED487D8F5E8ED97ABAF3023139F5C79EFEF771F58B3E6B254BEA71F01EB593614630D06D3638E40E5A9312FEB42DE570569D2A8D350950D435B6F9C28255211599FE55C4C0E94A63FC19F1EFCF2FD3B2E1F7C5A19AE53288E9C99EA7F6CD7DBBFE8686EDDA15B6EB3749ACF0E66AA7F8BF7649E0A7BA351A9C37D725146121525D5B3A44E39132D7F8933EB916F69C1C15BAF19FAE537B70E1F129BFF1560003A0DBCDD2406934B0000000049454E44AE426082,'image/png','<script type=\"text/javascript\">\r\n 	<!--\r\n 	function verification(entree) {\r\n 	var seulement_ceci =\"0123456789[]()-+*%/.\";\r\n 	for (var i = 0; i < entree.length; i++)\r\n 	if (seulement_ceci.indexOf(entree.charAt(i))<0 ) return false;\r\n 	return true;\r\n 	}\r\n 	\r\n 	function resultat() {\r\n 	var x = 0;\r\n	if (verification(window.document.calculatrice.affiche.value))\r\n 	x = eval(window.document.calculatrice.affiche.value);\r\n 	window.document.calculatrice.affiche.value = x;\r\n 	}\r\n 	\r\n 	function ajouter(caracteres) {\r\n 	window.document.calculatrice.affiche.value =\r\n 	window.document.calculatrice.affiche.value + caracteres;\r\n 	}\r\n 	\r\n 	function fonction_speciale(fonction) {\r\n 	if (verification(window.document.calculatrice.affiche.value)) {\r\n 	if(fonction == \"sqrt\") {\r\n 	var x = 0;\r\n 	x = eval(window.document.calculatrice.affiche.value);\r\n 	window.document.calculatrice.affiche.value = Math.sqrt(x);\r\n 	}\r\n 	if(fonction == \"pow\") {\r\n 	var x = 0;\r\n 	x = eval(window.document.calculatrice.affiche.value);\r\n 	window.document.calculatrice.affiche.value = x * x;\r\n 	}\r\n 	if(fonction == \"log\") {\r\n 	var x = 0;\r\n 	x = eval(window.document.calculatrice.affiche.value);\r\n 	window.document.calculatrice.affiche.value = Math.log(x);\r\n 	}\r\n 	} else window.document.calculatrice.affiche.value = 0\r\n 	}\r\n 	//-->\r\n 	</script>\r\n	<style type=\"text/css\">\r\n 	.button { width:40px; text-align:center;\r\n 	font-family:System,sans-serif;\r\n 	font-size:100%; }\r\n 	.affiche { width:100%; text-align:right;\r\n 	font-family:System,sans-serif;\r\n 	font-size:100%; }\r\n 	</style>\r\n 	<form name=\"calculatrice\" action=\"\" onSubmit=\"resultat();return false;\">\r\n 	<table border=\"5\" cellpadding=\"10\" cellspacing=\"0\">\r\n 	<tr>\r\n 	<td bgcolor=\"#C0C0C0\">\r\n	<input type=\"text\" name=\"affiche\" align=\"right\" class=\"affiche\"></td>\r\n 	</tr><tr>\r\n 	<td bgcolor=\"#E0E0E0\">\r\n 	<table border=\"0\" cellpadding=\"0\" cellspacing=\"2\">\r\n 	<tr>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 7 \" onClick=\"ajouter(\'7\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 8 \" onClick=\"ajouter(\'8\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 9 \" onClick=\"ajouter(\'9\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" + \" onClick=\"ajouter(\'+\')\"></td>\r\n 	</tr>\r\n 	<tr>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 4 \" onClick=\"ajouter(\'4\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 5 \" onClick=\"ajouter(\'5\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 6 \" onClick=\"ajouter(\'6\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" - \" onClick=\"ajouter(\'-\')\"></td>\r\n 	</tr>\r\n 	<tr>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 1 \" onClick=\"ajouter(\'1\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 2 \" onClick=\"ajouter(\'2\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 3 \" onClick=\"ajouter(\'3\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" * \" onClick=\"ajouter(\'*\')\"></td>\r\n 	</tr>\r\n 	<tr>\r\n 	<td><input type=\"button\" class=\"button\" value=\" = \" onClick=\"resultat()\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" 0 \" onClick=\"ajouter(\'0\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" . \" onClick=\"ajouter(\'.\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" / \" onClick=\"ajouter(\'/\')\"></td>\r\n 	</tr>\r\n 	<tr>\r\n 	<td><input type=\"button\" class=\"button\" value=\"sqrt \" onClick=\"fonction_speciale(\'sqrt\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" pow \" onClick=\"fonction_speciale(\'pow\')\"></td>\r\n 	<td><input type=\"button\" class=\"button\" value=\" log \" onClick=\"fonction_speciale(\'log\')\"></td>\r\n 	<td><input type=\"reset\" class=\"button\" value=\" C \"></td>\r\n 	</tr>\r\n 	</table>\r\n 	</td></tr></table>\r\n 	</form>\r\n',0,2,0,0),
(2,'Météo','Affichage des prévisions météorologiques',1,'html',0x89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA20000001974455874536F6674776172650041646F626520496D616765526561647971C9653C000009B84944415478DA54577D6C5D6519FF9DEF733FDADBF6AE1F2B5DDBB55BC78650B601226E630C83A0C640C00F50084130C4C4841804F51F1305E40FA2D118634C50026410080E7566C6652C088CB10DB68E8D51BAAD6B3B58BF6ECBEDFD3A5FF71C7FCF7BCB8CB77D7B4EDF73DEF7797ECFF37B7ECF7BB52449B0EBAD9358DD91D30F9CFEF8F1BFBF73FA6EDDB49BAB61044B03F80BCD30209F8443E78879A7EE351D9A86FFFB68EA476E1A0F4C5E527A5CEF6871FE7ACFF62B1EB9A2BFBBA069063431BCFFE898F5F0CEFD87924CEB95564B0BB4B40DDB34B8B90E4BD760D2B0A6C9BDAE0CDBBC1AB2989B3A26E7743AA6C91C8738C4FF350EC368CC7BE52ACE8D7D84E2C2F9C21FEEBFE9C6ABD6F68D8861E3AEDFBCF2EEC87C349C1EB804473EBE0044316039B2835A28585DC3525771A0D5B490241A16FC0829CB4636E5226524B0F986B16C54A38B31DF252EA41D1BDBB877E1EC199C3AF56EF8CA2377F41ADDD77DEDC997472EDC9EBDB41FEF9E9D84E638309D144CD344936520CB6BAB6D236DE8C818263AB8498AD7949E60538B8D0E5A2B7B3E9A5D17AD7C66F399CB21D71411B73916328CF789E902D60CF4A35CAC19274F9FEED56E79ECC5DA949B734F54CAD06984F985F89B56613450ABEB28966A58914BA39B1BD7E318F37E8CB54D3A5EFFCE46C45C737C7C1A0FBD7E0125B3094D349210A9CE3CA4CD463AE24492A661CEAFE3AA8E1CAA67473C9DD13253DC943B42673825476946D7B14C4C2F85480755DCB7AE095AB586A972089BE867963C6CCE3BD0B3CD30DD3436ADEF436FD6C052102BA3422B5719D594514537998CEB08852FB6ABE94450B3851C86ADC891D61BBC2C793136B79AD8F3AD8D78FAAEED78F1D60DC8C5151C3DB7803E37C6C35BD6A8FCD5AA9E62EFEAB62614031AD22D2C453A26176B98AD4624A9A908E945754489E04B6404A641AF427187444AF10D312E8E2CCC15F1E84DC3181E5C05DF0FB0E3730338D899C3EE93D3B876751E3D5D79653451660D6CE9CBE1B74746513232D8DCE1E2AB9BDA70E87C11BB26CB18684B238C0539231247BC2660312470F4866193465DDD6C6C46A6EE1E9DC53736F6431E5719EACEF63CBEB73DAF4C5568545B2E578FE4BA717005F6DF99A8DC0E77E7A0A79B70DB5011E3AF8CE2E49209668645515746D1283943D5292B9A0E18CBE29060555B335E3BBB88B1A95958B6ADA4A14A63B51A431B85C8A41DA4D32C239B8E124D4C839BD7AEC2C6353D482C17954A0D46268707AE68C37CB1D8901FE626619EC5B4A9144985D754CC8B96EF9782048BB34B38B758C5DA5E59C33AB52C3A61A25E2D617CC6434042F6E45C34E7B2248EA68CE90C4F4C47329914E60A0B78F29D19AC6CC92AB4318D46F5500133E58FCB305B3418D26BBFE2C1D1EA589BB1F0EDAF5F866BD6AC543996BAA656E02F6F9CC00B63352C862C19BA9EE77B9F6F4BF0C8F50370C8F02008E1D8167CAF861FFEF3237C5075B021AF73DE879FC4F009CE6E48A9A65857E7DFA8EAE3D75B7BB075B003BDCD29584DCD88A3081E3793B03EF3C6497C7FDF0C7A3ABBD0EE525028300B750DBF3A368F26770A3FBA710322BE6BB214F78E9CC14BA7CBD8B66E15C2DA120D268AD584CD70C7E2B4304C27C5750C640CDCFBC5210C5ED2092B9D51F9F402CA221144B50A769EF6D0D3D1895E967DABAD3152095A4C8A425F07768F5731333B07374591A9D7B1A16705BEB4D2C0C133533857658809CCA893D18AD510C36C02147C485E8800A177B1D348AE54A3A1184C2ED4F029EBB43D65287190328C15AB35503971A618E2AD8962A3297801FA586EFFB8E76AFCFCEA360C6548A820A04391422BF5AFCB429D3ADCC50D67230B5F797E04CFBC7982B10F481057914A18EDD71BACCC925CC645456A0892C7165AE373AF1E371CD51A44733219FCF4964DD8F7DD6174A7629CAF86080826E2D00DAE94D6E7B0A49C5406872B161EDCF7316E7BEE30266616E1529F9968AC6A71B02263836244A3B1DA5C5B56A422E73A5DE0AA95D9FFF5653EAFD5FCC63F4E1616A3E9B3EBF971A360752C37F4885207B2791DF37C1D5BD8AB9309767F380B8361F529EED9E62CAECB2738767E1EE584F2CAEEC38A432936706462015BBB5D0CF57592CDBE0A83AA718A10638C02733F477DD768BC26AD5578A5BC93C64ED42EA7A49F2EF80906733A65B0458537224283A87EBC6D009639897F4D2CE2FD995085B78B7BDF7FA98D9F6CED252774C50B8B7AB8F7E414F68ECD60AC50C25429C04C62224D6CFA670222FC1156A7443535AA10DBE227D30B78EA0B6D18EEEF8247668B0C4A2D67C8F4476FDA80BBA7E7F0F6144B8439DDD495C1A5FD9D4A8B24AFC28B4F1716F1C0DF8E62A26AA1256B2B254B9B743EF4A9D9B122A5299B8A62A9E30AF33CF1A987FB8632B867EB0684248D78277D59676DFA64AB7497EEAE76DCDED571319F125E51248D5113D6BA7C77755B0E15D2A3891A2D7915A41EAF812016564B7E754A2496FB689A27908F8A7CA9BC4442F014916214E8E9E27C813D5A57A4A95268A4C605614281715C0719D6AF68B6EF8770293C43ED19CC57AA3444A41C21D31570C4714344948028D49A34080397642DCA9C8B87F68CE3D5F7CEE289D7C6F1C09E496C79F603BCF4DE241DB12FD6B8238C676DFEE7E828CBDF53FABC5CFAB863A895502B0A5D409D56486950502B0191A21086C60AB9A68EB4EBF2291C2D91306FCEE3B9D11A8E2C0017CC3C9E7F7F4E6DE6DA0609C4AE4612FD6CCF715CFFEC71DCF0E70338347A4EB1593EFF1E635763F482385446ABCB1ED5895AA4539A8429AC16CF5246E39CEC337CAD2E7B68BA157E182AF11864294CF3E85328D690EFC8F264011CF8601C4F1DBA80F5EBFB71A2B0849B5F3886C7B72FE1F84C117F7CBF80F67C13D52A8027270FB14B1E4887A2F888DF51AA54AA20CB3CE9EA60D63894EBECCFB59021AA27EADE4E22CC85094ECD95B1A5BD55B5B9DF1D9E82994E215BF7319073301DDAF8C1DEB3542483878626B6ED00356E28F5AE713DD8A16C56821DD735ED8997F7EFDC335AB9F3DAEDDBB0EFC373E8E2615ED07B147A755C49445B6355F482DED6EB58E1484B8C7062911B49AFE4BB652EF25503D0C87CC969A48C468A1011A272192B5776A2E5420137F466DED0CA9E97BDF9B19D73836B37BAB9BED578EBCC79A289199A86C20832A5D75C1C3222051E6D2B64B59C9DDAD98B8530253EF768CCA003BE62AF10096A1F092F88BCBFBB1383FC1270FED487D8F5E8ED97ABAF3023139F5C79EFEF771F58B3E6B254BEA71F01EB593614630D06D3638E40E5A9312FEB42DE570569D2A8D350950D435B6F9C28255211599FE55C4C0E94A63FC19F1EFCF2FD3B2E1F7C5A19AE53288E9C99EA7F6CD7DBBFE8686EDDA15B6EB3749ACF0E66AA7F8BF7649E0A7BA351A9C37D725146121525D5B3A44E39132D7F8933EB916F69C1C15BAF19FAE537B70E1F129BFF1560003A0DBCDD2406934B0000000049454E44AE426082,'image/png','<!-- Yahoo! Weather Badge -->\r\n<iframe allowtransparency=\"true\" marginwidth=\"0\" marginheight=\"0\" hspace=\"0\" vspace=\"0\" frameborder=\"0\" scrolling=\"no\" src=\"http://fr.meteo.yahoo.com/badge/?id=615702&u=c&t=trans&l=vertical\" height=\"255px\" width=\"260px\">\r\n	\r\n</iframe><noscript><a href=\"http://fr.meteo.yahoo.com/france/île-de-france/paris-615702/\">Paris Weather</a> </noscript>\r\n<!-- Yahoo! Weather Badge -->\r\n',0,2,0,0),
(3,'Calendrier','Calendrier',1,'html',0x89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA20000001974455874536F6674776172650041646F626520496D616765526561647971C9653C000009B84944415478DA54577D6C5D6519FF9DEF733FDADBF6AE1F2B5DDBB55BC78650B601226E630C83A0C640C00F50084130C4C4841804F51F1305E40FA2D118634C50026410080E7566C6652C088CB10DB68E8D51BAAD6B3B58BF6ECBEDFD3A5FF71C7FCF7BCB8CB77D7B4EDF73DEF7797ECFF37B7ECF7BB52449B0EBAD9358DD91D30F9CFEF8F1BFBF73FA6EDDB49BAB61044B03F80BCD30209F8443E78879A7EE351D9A86FFFB68EA476E1A0F4C5E527A5CEF6871FE7ACFF62B1EB9A2BFBBA069063431BCFFE898F5F0CEFD87924CEB95564B0BB4B40DDB34B8B90E4BD760D2B0A6C9BDAE0CDBBC1AB2989B3A26E7743AA6C91C8738C4FF350EC368CC7BE52ACE8D7D84E2C2F9C21FEEBFE9C6ABD6F68D8861E3AEDFBCF2EEC87C349C1EB804473EBE0044316039B2835A28585DC3525771A0D5B490241A16FC0829CB4636E5226524B0F986B16C54A38B31DF252EA41D1BDBB877E1EC199C3AF56EF8CA2377F41ADDD77DEDC997472EDC9EBDB41FEF9E9D84E638309D144CD344936520CB6BAB6D236DE8C818263AB8498AD7949E60538B8D0E5A2B7B3E9A5D17AD7C66F399CB21D71411B73916328CF789E902D60CF4A35CAC19274F9FEED56E79ECC5DA949B734F54CAD06984F985F89B56613450ABEB28966A58914BA39B1BD7E318F37E8CB54D3A5EFFCE46C45C737C7C1A0FBD7E0125B3094D349210A9CE3CA4CD463AE24492A661CEAFE3AA8E1CAA67473C9DD13253DC943B42673825476946D7B14C4C2F85480755DCB7AE095AB586A972089BE867963C6CCE3BD0B3CD30DD3436ADEF436FD6C052102BA3422B5719D594514537998CEB08852FB6ABE94450B3851C86ADC891D61BBC2C793136B79AD8F3AD8D78FAAEED78F1D60DC8C5151C3DB7803E37C6C35BD6A8FCD5AA9E62EFEAB62614031AD22D2C453A26176B98AD4624A9A908E945754489E04B6404A641AF427187444AF10D312E8E2CCC15F1E84DC3181E5C05DF0FB0E3730338D899C3EE93D3B876751E3D5D79653451660D6CE9CBE1B74746513232D8DCE1E2AB9BDA70E87C11BB26CB18684B238C0539231247BC2660312470F4866193465DDD6C6C46A6EE1E9DC53736F6431E5719EACEF63CBEB73DAF4C5568545B2E578FE4BA717005F6DF99A8DC0E77E7A0A79B70DB5011E3AF8CE2E49209668645515746D1283943D5292B9A0E18CBE29060555B335E3BBB88B1A95958B6ADA4A14A63B51A431B85C8A41DA4D32C239B8E124D4C839BD7AEC2C6353D482C17954A0D46268707AE68C37CB1D8901FE626619EC5B4A9144985D754CC8B96EF9782048BB34B38B758C5DA5E59C33AB52C3A61A25E2D617CC6434042F6E45C34E7B2248EA68CE90C4F4C47329914E60A0B78F29D19AC6CC92AB4318D46F5500133E58FCB305B3418D26BBFE2C1D1EA589BB1F0EDAF5F866BD6AC543996BAA656E02F6F9CC00B63352C862C19BA9EE77B9F6F4BF0C8F50370C8F02008E1D8167CAF861FFEF3237C5075B021AF73DE879FC4F009CE6E48A9A65857E7DFA8EAE3D75B7BB075B003BDCD29584DCD88A3081E3793B03EF3C6497C7FDF0C7A3ABBD0EE525028300B750DBF3A368F26770A3FBA710322BE6BB214F78E9CC14BA7CBD8B66E15C2DA120D268AD584CD70C7E2B4304C27C5750C640CDCFBC5210C5ED2092B9D51F9F402CA221144B50A769EF6D0D3D1895E967DABAD3152095A4C8A425F07768F5731333B07374591A9D7B1A16705BEB4D2C0C133533857658809CCA893D18AD510C36C02147C485E8800A177B1D348AE54A3A1184C2ED4F029EBB43D65287190328C15AB35503971A618E2AD8962A3297801FA586EFFB8E76AFCFCEA360C6548A820A04391422BF5AFCB429D3ADCC50D67230B5F797E04CFBC7982B10F481057914A18EDD71BACCC925CC645456A0892C7165AE373AF1E371CD51A44733219FCF4964DD8F7DD6174A7629CAF86080826E2D00DAE94D6E7B0A49C5406872B161EDCF7316E7BEE30266616E1529F9968AC6A71B02263836244A3B1DA5C5B56A422E73A5DE0AA95D9FFF5653EAFD5FCC63F4E1616A3E9B3EBF971A360752C37F4885207B2791DF37C1D5BD8AB9309767F380B8361F529EED9E62CAECB2738767E1EE584F2CAEEC38A432936706462015BBB5D0CF57592CDBE0A83AA718A10638C02733F477DD768BC26AD5578A5BC93C64ED42EA7A49F2EF80906733A65B0458537224283A87EBC6D009639897F4D2CE2FD995085B78B7BDF7FA98D9F6CED252774C50B8B7AB8F7E414F68ECD60AC50C25429C04C62224D6CFA670222FC1156A7443535AA10DBE227D30B78EA0B6D18EEEF8247668B0C4A2D67C8F4476FDA80BBA7E7F0F6144B8439DDD495C1A5FD9D4A8B24AFC28B4F1716F1C0DF8E62A26AA1256B2B254B9B743EF4A9D9B122A5299B8A62A9E30AF33CF1A987FB8632B867EB0684248D78277D59676DFA64AB7497EEAE76DCDED571319F125E51248D5113D6BA7C77755B0E15D2A3891A2D7915A41EAF812016564B7E754A2496FB689A27908F8A7CA9BC4442F014916214E8E9E27C813D5A57A4A95268A4C605614281715C0719D6AF68B6EF8770293C43ED19CC57AA3444A41C21D31570C4714344948028D49A34080397642DCA9C8B87F68CE3D5F7CEE289D7C6F1C09E496C79F603BCF4DE241DB12FD6B8238C676DFEE7E828CBDF53FABC5CFAB863A895502B0A5D409D56486950502B0191A21086C60AB9A68EB4EBF2291C2D91306FCEE3B9D11A8E2C0017CC3C9E7F7F4E6DE6DA0609C4AE4612FD6CCF715CFFEC71DCF0E70338347A4EB1593EFF1E635763F482385446ABCB1ED5895AA4539A8429AC16CF5246E39CEC337CAD2E7B68BA157E182AF11864294CF3E85328D690EFC8F264011CF8601C4F1DBA80F5EBFB71A2B0849B5F3886C7B72FE1F84C117F7CBF80F67C13D52A8027270FB14B1E4887A2F888DF51AA54AA20CB3CE9EA60D63894EBECCFB59021AA27EADE4E22CC85094ECD95B1A5BD55B5B9DF1D9E82994E215BF7319073301DDAF8C1DEB3542483878626B6ED00356E28F5AE713DD8A16C56821DD735ED8997F7EFDC335AB9F3DAEDDBB0EFC373E8E2615ED07B147A755C49445B6355F482DED6EB58E1484B8C7062911B49AFE4BB652EF25503D0C87CC969A48C468A1011A272192B5776A2E5420137F466DED0CA9E97BDF9B19D73836B37BAB9BED578EBCC79A289199A86C20832A5D75C1C3222051E6D2B64B59C9DDAD98B8530253EF768CCA003BE62AF10096A1F092F88BCBFBB1383FC1270FED487D8F5E8ED97ABAF3023139F5C79EFEF771F58B3E6B254BEA71F01EB593614630D06D3638E40E5A9312FEB42DE570569D2A8D350950D435B6F9C28255211599FE55C4C0E94A63FC19F1EFCF2FD3B2E1F7C5A19AE53288E9C99EA7F6CD7DBBFE8686EDDA15B6EB3749ACF0E66AA7F8BF7649E0A7BA351A9C37D725146121525D5B3A44E39132D7F8933EB916F69C1C15BAF19FAE537B70E1F129BFF1560003A0DBCDD2406934B0000000049454E44AE426082,'image/png','<!-- Google Calendar Element Code -->\r\n<iframe frameborder=0 marginwidth=0 marginheight=0 border=0 style=\"border:0;margin:0;width:260px;height:300px;\" src=\"http://www.google.com/calendar/embed?showTitle=0&showTabs=0&showPrint=0&showCalendars=0&wkst=1&element=true&src=usa__en%40holiday.calendar.google.com\" scrolling=\"no\" allowtransparency=\"true\"></iframe>',0,2,0,0),
(4,'Google','Recherche Google',1,'html',0x89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA20000001974455874536F6674776172650041646F626520496D616765526561647971C9653C000009B84944415478DA54577D6C5D6519FF9DEF733FDADBF6AE1F2B5DDBB55BC78650B601226E630C83A0C640C00F50084130C4C4841804F51F1305E40FA2D118634C50026410080E7566C6652C088CB10DB68E8D51BAAD6B3B58BF6ECBEDFD3A5FF71C7FCF7BCB8CB77D7B4EDF73DEF7797ECFF37B7ECF7BB52449B0EBAD9358DD91D30F9CFEF8F1BFBF73FA6EDDB49BAB61044B03F80BCD30209F8443E78879A7EE351D9A86FFFB68EA476E1A0F4C5E527A5CEF6871FE7ACFF62B1EB9A2BFBBA069063431BCFFE898F5F0CEFD87924CEB95564B0BB4B40DDB34B8B90E4BD760D2B0A6C9BDAE0CDBBC1AB2989B3A26E7743AA6C91C8738C4FF350EC368CC7BE52ACE8D7D84E2C2F9C21FEEBFE9C6ABD6F68D8861E3AEDFBCF2EEC87C349C1EB804473EBE0044316039B2835A28585DC3525771A0D5B490241A16FC0829CB4636E5226524B0F986B16C54A38B31DF252EA41D1BDBB877E1EC199C3AF56EF8CA2377F41ADDD77DEDC997472EDC9EBDB41FEF9E9D84E638309D144CD344936520CB6BAB6D236DE8C818263AB8498AD7949E60538B8D0E5A2B7B3E9A5D17AD7C66F399CB21D71411B73916328CF789E902D60CF4A35CAC19274F9FEED56E79ECC5DA949B734F54CAD06984F985F89B56613450ABEB28966A58914BA39B1BD7E318F37E8CB54D3A5EFFCE46C45C737C7C1A0FBD7E0125B3094D349210A9CE3CA4CD463AE24492A661CEAFE3AA8E1CAA67473C9DD13253DC943B42673825476946D7B14C4C2F85480755DCB7AE095AB586A972089BE867963C6CCE3BD0B3CD30DD3436ADEF436FD6C052102BA3422B5719D594514537998CEB08852FB6ABE94450B3851C86ADC891D61BBC2C793136B79AD8F3AD8D78FAAEED78F1D60DC8C5151C3DB7803E37C6C35BD6A8FCD5AA9E62EFEAB62614031AD22D2C453A26176B98AD4624A9A908E945754489E04B6404A641AF427187444AF10D312E8E2CCC15F1E84DC3181E5C05DF0FB0E3730338D899C3EE93D3B876751E3D5D79653451660D6CE9CBE1B74746513232D8DCE1E2AB9BDA70E87C11BB26CB18684B238C0539231247BC2660312470F4866193465DDD6C6C46A6EE1E9DC53736F6431E5719EACEF63CBEB73DAF4C5568545B2E578FE4BA717005F6DF99A8DC0E77E7A0A79B70DB5011E3AF8CE2E49209668645515746D1283943D5292B9A0E18CBE29060555B335E3BBB88B1A95958B6ADA4A14A63B51A431B85C8A41DA4D32C239B8E124D4C839BD7AEC2C6353D482C17954A0D46268707AE68C37CB1D8901FE626619EC5B4A9144985D754CC8B96EF9782048BB34B38B758C5DA5E59C33AB52C3A61A25E2D617CC6434042F6E45C34E7B2248EA68CE90C4F4C47329914E60A0B78F29D19AC6CC92AB4318D46F5500133E58FCB305B3418D26BBFE2C1D1EA589BB1F0EDAF5F866BD6AC543996BAA656E02F6F9CC00B63352C862C19BA9EE77B9F6F4BF0C8F50370C8F02008E1D8167CAF861FFEF3237C5075B021AF73DE879FC4F009CE6E48A9A65857E7DFA8EAE3D75B7BB075B003BDCD29584DCD88A3081E3793B03EF3C6497C7FDF0C7A3ABBD0EE525028300B750DBF3A368F26770A3FBA710322BE6BB214F78E9CC14BA7CBD8B66E15C2DA120D268AD584CD70C7E2B4304C27C5750C640CDCFBC5210C5ED2092B9D51F9F402CA221144B50A769EF6D0D3D1895E967DABAD3152095A4C8A425F07768F5731333B07374591A9D7B1A16705BEB4D2C0C133533857658809CCA893D18AD510C36C02147C485E8800A177B1D348AE54A3A1184C2ED4F029EBB43D65287190328C15AB35503971A618E2AD8962A3297801FA586EFFB8E76AFCFCEA360C6548A820A04391422BF5AFCB429D3ADCC50D67230B5F797E04CFBC7982B10F481057914A18EDD71BACCC925CC645456A0892C7165AE373AF1E371CD51A44733219FCF4964DD8F7DD6174A7629CAF86080826E2D00DAE94D6E7B0A49C5406872B161EDCF7316E7BEE30266616E1529F9968AC6A71B02263836244A3B1DA5C5B56A422E73A5DE0AA95D9FFF5653EAFD5FCC63F4E1616A3E9B3EBF971A360752C37F4885207B2791DF37C1D5BD8AB9309767F380B8361F529EED9E62CAECB2738767E1EE584F2CAEEC38A432936706462015BBB5D0CF57592CDBE0A83AA718A10638C02733F477DD768BC26AD5578A5BC93C64ED42EA7A49F2EF80906733A65B0458537224283A87EBC6D009639897F4D2CE2FD995085B78B7BDF7FA98D9F6CED252774C50B8B7AB8F7E414F68ECD60AC50C25429C04C62224D6CFA670222FC1156A7443535AA10DBE227D30B78EA0B6D18EEEF8247668B0C4A2D67C8F4476FDA80BBA7E7F0F6144B8439DDD495C1A5FD9D4A8B24AFC28B4F1716F1C0DF8E62A26AA1256B2B254B9B743EF4A9D9B122A5299B8A62A9E30AF33CF1A987FB8632B867EB0684248D78277D59676DFA64AB7497EEAE76DCDED571319F125E51248D5113D6BA7C77755B0E15D2A3891A2D7915A41EAF812016564B7E754A2496FB689A27908F8A7CA9BC4442F014916214E8E9E27C813D5A57A4A95268A4C605614281715C0719D6AF68B6EF8770293C43ED19CC57AA3444A41C21D31570C4714344948028D49A34080397642DCA9C8B87F68CE3D5F7CEE289D7C6F1C09E496C79F603BCF4DE241DB12FD6B8238C676DFEE7E828CBDF53FABC5CFAB863A895502B0A5D409D56486950502B0191A21086C60AB9A68EB4EBF2291C2D91306FCEE3B9D11A8E2C0017CC3C9E7F7F4E6DE6DA0609C4AE4612FD6CCF715CFFEC71DCF0E70338347A4EB1593EFF1E635763F482385446ABCB1ED5895AA4539A8429AC16CF5246E39CEC337CAD2E7B68BA157E182AF11864294CF3E85328D690EFC8F264011CF8601C4F1DBA80F5EBFB71A2B0849B5F3886C7B72FE1F84C117F7CBF80F67C13D52A8027270FB14B1E4887A2F888DF51AA54AA20CB3CE9EA60D63894EBECCFB59021AA27EADE4E22CC85094ECD95B1A5BD55B5B9DF1D9E82994E215BF7319073301DDAF8C1DEB3542483878626B6ED00356E28F5AE713DD8A16C56821DD735ED8997F7EFDC335AB9F3DAEDDBB0EFC373E8E2615ED07B147A755C49445B6355F482DED6EB58E1484B8C7062911B49AFE4BB652EF25503D0C87CC969A48C468A1011A272192B5776A2E5420137F466DED0CA9E97BDF9B19D73836B37BAB9BED578EBCC79A289199A86C20832A5D75C1C3222051E6D2B64B59C9DDAD98B8530253EF768CCA003BE62AF10096A1F092F88BCBFBB1383FC1270FED487D8F5E8ED97ABAF3023139F5C79EFEF771F58B3E6B254BEA71F01EB593614630D06D3638E40E5A9312FEB42DE570569D2A8D350950D435B6F9C28255211599FE55C4C0E94A63FC19F1EFCF2FD3B2E1F7C5A19AE53288E9C99EA7F6CD7DBBFE8686EDDA15B6EB3749ACF0E66AA7F8BF7649E0A7BA351A9C37D725146121525D5B3A44E39132D7F8933EB916F69C1C15BAF19FAE537B70E1F129BFF1560003A0DBCDD2406934B0000000049454E44AE426082,'image/png','<SCRIPT LANGUAGE=JAVASCRIPT>\r\nfunction googleSearch() {\r\n  if (document.doingasearch.searchbox.value!=\"\") {    \r\n      window.open(\"http://www.google.com/search?hl=en&q=\" + document.doingasearch.searchbox.value) ;        \r\n  }\r\n  else {\r\n    alert(\"Veuillez saisir un texte de recherche.\")\r\n    document.doingasearch.searchbox.focus();\r\n  }\r\n}\r\n</SCRIPT>\r\n  <FORM name=\"doingasearch\" id=\"doingasearch\">\r\n   Google  \r\n  <INPUT name=searchbox type=text size=20>\r\n  <INPUT type=\"button\" value=\"Search\" onclick=\"return googleSearch();\">\r\n</FORM>',0,2,0,0),
(5,'Google Maps','Google Maps',1,'html',0x89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA20000001974455874536F6674776172650041646F626520496D616765526561647971C9653C000009B84944415478DA54577D6C5D6519FF9DEF733FDADBF6AE1F2B5DDBB55BC78650B601226E630C83A0C640C00F50084130C4C4841804F51F1305E40FA2D118634C50026410080E7566C6652C088CB10DB68E8D51BAAD6B3B58BF6ECBEDFD3A5FF71C7FCF7BCB8CB77D7B4EDF73DEF7797ECFF37B7ECF7BB52449B0EBAD9358DD91D30F9CFEF8F1BFBF73FA6EDDB49BAB61044B03F80BCD30209F8443E78879A7EE351D9A86FFFB68EA476E1A0F4C5E527A5CEF6871FE7ACFF62B1EB9A2BFBBA069063431BCFFE898F5F0CEFD87924CEB95564B0BB4B40DDB34B8B90E4BD760D2B0A6C9BDAE0CDBBC1AB2989B3A26E7743AA6C91C8738C4FF350EC368CC7BE52ACE8D7D84E2C2F9C21FEEBFE9C6ABD6F68D8861E3AEDFBCF2EEC87C349C1EB804473EBE0044316039B2835A28585DC3525771A0D5B490241A16FC0829CB4636E5226524B0F986B16C54A38B31DF252EA41D1BDBB877E1EC199C3AF56EF8CA2377F41ADDD77DEDC997472EDC9EBDB41FEF9E9D84E638309D144CD344936520CB6BAB6D236DE8C818263AB8498AD7949E60538B8D0E5A2B7B3E9A5D17AD7C66F399CB21D71411B73916328CF789E902D60CF4A35CAC19274F9FEED56E79ECC5DA949B734F54CAD06984F985F89B56613450ABEB28966A58914BA39B1BD7E318F37E8CB54D3A5EFFCE46C45C737C7C1A0FBD7E0125B3094D349210A9CE3CA4CD463AE24492A661CEAFE3AA8E1CAA67473C9DD13253DC943B42673825476946D7B14C4C2F85480755DCB7AE095AB586A972089BE867963C6CCE3BD0B3CD30DD3436ADEF436FD6C052102BA3422B5719D594514537998CEB08852FB6ABE94450B3851C86ADC891D61BBC2C793136B79AD8F3AD8D78FAAEED78F1D60DC8C5151C3DB7803E37C6C35BD6A8FCD5AA9E62EFEAB62614031AD22D2C453A26176B98AD4624A9A908E945754489E04B6404A641AF427187444AF10D312E8E2CCC15F1E84DC3181E5C05DF0FB0E3730338D899C3EE93D3B876751E3D5D79653451660D6CE9CBE1B74746513232D8DCE1E2AB9BDA70E87C11BB26CB18684B238C0539231247BC2660312470F4866193465DDD6C6C46A6EE1E9DC53736F6431E5719EACEF63CBEB73DAF4C5568545B2E578FE4BA717005F6DF99A8DC0E77E7A0A79B70DB5011E3AF8CE2E49209668645515746D1283943D5292B9A0E18CBE29060555B335E3BBB88B1A95958B6ADA4A14A63B51A431B85C8A41DA4D32C239B8E124D4C839BD7AEC2C6353D482C17954A0D46268707AE68C37CB1D8901FE626619EC5B4A9144985D754CC8B96EF9782048BB34B38B758C5DA5E59C33AB52C3A61A25E2D617CC6434042F6E45C34E7B2248EA68CE90C4F4C47329914E60A0B78F29D19AC6CC92AB4318D46F5500133E58FCB305B3418D26BBFE2C1D1EA589BB1F0EDAF5F866BD6AC543996BAA656E02F6F9CC00B63352C862C19BA9EE77B9F6F4BF0C8F50370C8F02008E1D8167CAF861FFEF3237C5075B021AF73DE879FC4F009CE6E48A9A65857E7DFA8EAE3D75B7BB075B003BDCD29584DCD88A3081E3793B03EF3C6497C7FDF0C7A3ABBD0EE525028300B750DBF3A368F26770A3FBA710322BE6BB214F78E9CC14BA7CBD8B66E15C2DA120D268AD584CD70C7E2B4304C27C5750C640CDCFBC5210C5ED2092B9D51F9F402CA221144B50A769EF6D0D3D1895E967DABAD3152095A4C8A425F07768F5731333B07374591A9D7B1A16705BEB4D2C0C133533857658809CCA893D18AD510C36C02147C485E8800A177B1D348AE54A3A1184C2ED4F029EBB43D65287190328C15AB35503971A618E2AD8962A3297801FA586EFFB8E76AFCFCEA360C6548A820A04391422BF5AFCB429D3ADCC50D67230B5F797E04CFBC7982B10F481057914A18EDD71BACCC925CC645456A0892C7165AE373AF1E371CD51A44733219FCF4964DD8F7DD6174A7629CAF86080826E2D00DAE94D6E7B0A49C5406872B161EDCF7316E7BEE30266616E1529F9968AC6A71B02263836244A3B1DA5C5B56A422E73A5DE0AA95D9FFF5653EAFD5FCC63F4E1616A3E9B3EBF971A360752C37F4885207B2791DF37C1D5BD8AB9309767F380B8361F529EED9E62CAECB2738767E1EE584F2CAEEC38A432936706462015BBB5D0CF57592CDBE0A83AA718A10638C02733F477DD768BC26AD5578A5BC93C64ED42EA7A49F2EF80906733A65B0458537224283A87EBC6D009639897F4D2CE2FD995085B78B7BDF7FA98D9F6CED252774C50B8B7AB8F7E414F68ECD60AC50C25429C04C62224D6CFA670222FC1156A7443535AA10DBE227D30B78EA0B6D18EEEF8247668B0C4A2D67C8F4476FDA80BBA7E7F0F6144B8439DDD495C1A5FD9D4A8B24AFC28B4F1716F1C0DF8E62A26AA1256B2B254B9B743EF4A9D9B122A5299B8A62A9E30AF33CF1A987FB8632B867EB0684248D78277D59676DFA64AB7497EEAE76DCDED571319F125E51248D5113D6BA7C77755B0E15D2A3891A2D7915A41EAF812016564B7E754A2496FB689A27908F8A7CA9BC4442F014916214E8E9E27C813D5A57A4A95268A4C605614281715C0719D6AF68B6EF8770293C43ED19CC57AA3444A41C21D31570C4714344948028D49A34080397642DCA9C8B87F68CE3D5F7CEE289D7C6F1C09E496C79F603BCF4DE241DB12FD6B8238C676DFEE7E828CBDF53FABC5CFAB863A895502B0A5D409D56486950502B0191A21086C60AB9A68EB4EBF2291C2D91306FCEE3B9D11A8E2C0017CC3C9E7F7F4E6DE6DA0609C4AE4612FD6CCF715CFFEC71DCF0E70338347A4EB1593EFF1E635763F482385446ABCB1ED5895AA4539A8429AC16CF5246E39CEC337CAD2E7B68BA157E182AF11864294CF3E85328D690EFC8F264011CF8601C4F1DBA80F5EBFB71A2B0849B5F3886C7B72FE1F84C117F7CBF80F67C13D52A8027270FB14B1E4887A2F888DF51AA54AA20CB3CE9EA60D63894EBECCFB59021AA27EADE4E22CC85094ECD95B1A5BD55B5B9DF1D9E82994E215BF7319073301DDAF8C1DEB3542483878626B6ED00356E28F5AE713DD8A16C56821DD735ED8997F7EFDC335AB9F3DAEDDBB0EFC373E8E2615ED07B147A755C49445B6355F482DED6EB58E1484B8C7062911B49AFE4BB652EF25503D0C87CC969A48C468A1011A272192B5776A2E5420137F466DED0CA9E97BDF9B19D73836B37BAB9BED578EBCC79A289199A86C20832A5D75C1C3222051E6D2B64B59C9DDAD98B8530253EF768CCA003BE62AF10096A1F092F88BCBFBB1383FC1270FED487D8F5E8ED97ABAF3023139F5C79EFEF771F58B3E6B254BEA71F01EB593614630D06D3638E40E5A9312FEB42DE570569D2A8D350950D435B6F9C28255211599FE55C4C0E94A63FC19F1EFCF2FD3B2E1F7C5A19AE53288E9C99EA7F6CD7DBBFE8686EDDA15B6EB3749ACF0E66AA7F8BF7649E0A7BA351A9C37D725146121525D5B3A44E39132D7F8933EB916F69C1C15BAF19FAE537B70E1F129BFF1560003A0DBCDD2406934B0000000049454E44AE426082,'image/png','<!-- Google Maps Element Code -->\r\n<iframe frameborder=0 marginwidth=0 marginheight=0 border=0 style=\"border:0;margin:0;width:260px;height:250px;\" src=\"http://www.google.com/uds/modules/elements/mapselement/iframe.html?maptype=hybrid&latlng=48.856667%2C2.350987&mlatlng=48.856667%2C2.350987&maddress1=Paris&maddress2=France&zoom=12&mtitle=Paris&element=true\" scrolling=\"no\" allowtransparency=\"true\"></iframe>',0,2,0,0),
(6,'Communication Interne','Les infos essentielles',2,'html',0x89504E470D0A1A0A0000000D494844520000001E0000001E08060000003B30AEA20000001974455874536F6674776172650041646F626520496D616765526561647971C9653C000009B84944415478DA54577D6C5D6519FF9DEF733FDADBF6AE1F2B5DDBB55BC78650B601226E630C83A0C640C00F50084130C4C4841804F51F1305E40FA2D118634C50026410080E7566C6652C088CB10DB68E8D51BAAD6B3B58BF6ECBEDFD3A5FF71C7FCF7BCB8CB77D7B4EDF73DEF7797ECFF37B7ECF7BB52449B0EBAD9358DD91D30F9CFEF8F1BFBF73FA6EDDB49BAB61044B03F80BCD30209F8443E78879A7EE351D9A86FFFB68EA476E1A0F4C5E527A5CEF6871FE7ACFF62B1EB9A2BFBBA069063431BCFFE898F5F0CEFD87924CEB95564B0BB4B40DDB34B8B90E4BD760D2B0A6C9BDAE0CDBBC1AB2989B3A26E7743AA6C91C8738C4FF350EC368CC7BE52ACE8D7D84E2C2F9C21FEEBFE9C6ABD6F68D8861E3AEDFBCF2EEC87C349C1EB804473EBE0044316039B2835A28585DC3525771A0D5B490241A16FC0829CB4636E5226524B0F986B16C54A38B31DF252EA41D1BDBB877E1EC199C3AF56EF8CA2377F41ADDD77DEDC997472EDC9EBDB41FEF9E9D84E638309D144CD344936520CB6BAB6D236DE8C818263AB8498AD7949E60538B8D0E5A2B7B3E9A5D17AD7C66F399CB21D71411B73916328CF789E902D60CF4A35CAC19274F9FEED56E79ECC5DA949B734F54CAD06984F985F89B56613450ABEB28966A58914BA39B1BD7E318F37E8CB54D3A5EFFCE46C45C737C7C1A0FBD7E0125B3094D349210A9CE3CA4CD463AE24492A661CEAFE3AA8E1CAA67473C9DD13253DC943B42673825476946D7B14C4C2F85480755DCB7AE095AB586A972089BE867963C6CCE3BD0B3CD30DD3436ADEF436FD6C052102BA3422B5719D594514537998CEB08852FB6ABE94450B3851C86ADC891D61BBC2C793136B79AD8F3AD8D78FAAEED78F1D60DC8C5151C3DB7803E37C6C35BD6A8FCD5AA9E62EFEAB62614031AD22D2C453A26176B98AD4624A9A908E945754489E04B6404A641AF427187444AF10D312E8E2CCC15F1E84DC3181E5C05DF0FB0E3730338D899C3EE93D3B876751E3D5D79653451660D6CE9CBE1B74746513232D8DCE1E2AB9BDA70E87C11BB26CB18684B238C0539231247BC2660312470F4866193465DDD6C6C46A6EE1E9DC53736F6431E5719EACEF63CBEB73DAF4C5568545B2E578FE4BA717005F6DF99A8DC0E77E7A0A79B70DB5011E3AF8CE2E49209668645515746D1283943D5292B9A0E18CBE29060555B335E3BBB88B1A95958B6ADA4A14A63B51A431B85C8A41DA4D32C239B8E124D4C839BD7AEC2C6353D482C17954A0D46268707AE68C37CB1D8901FE626619EC5B4A9144985D754CC8B96EF9782048BB34B38B758C5DA5E59C33AB52C3A61A25E2D617CC6434042F6E45C34E7B2248EA68CE90C4F4C47329914E60A0B78F29D19AC6CC92AB4318D46F5500133E58FCB305B3418D26BBFE2C1D1EA589BB1F0EDAF5F866BD6AC543996BAA656E02F6F9CC00B63352C862C19BA9EE77B9F6F4BF0C8F50370C8F02008E1D8167CAF861FFEF3237C5075B021AF73DE879FC4F009CE6E48A9A65857E7DFA8EAE3D75B7BB075B003BDCD29584DCD88A3081E3793B03EF3C6497C7FDF0C7A3ABBD0EE525028300B750DBF3A368F26770A3FBA710322BE6BB214F78E9CC14BA7CBD8B66E15C2DA120D268AD584CD70C7E2B4304C27C5750C640CDCFBC5210C5ED2092B9D51F9F402CA221144B50A769EF6D0D3D1895E967DABAD3152095A4C8A425F07768F5731333B07374591A9D7B1A16705BEB4D2C0C133533857658809CCA893D18AD510C36C02147C485E8800A177B1D348AE54A3A1184C2ED4F029EBB43D65287190328C15AB35503971A618E2AD8962A3297801FA586EFFB8E76AFCFCEA360C6548A820A04391422BF5AFCB429D3ADCC50D67230B5F797E04CFBC7982B10F481057914A18EDD71BACCC925CC645456A0892C7165AE373AF1E371CD51A44733219FCF4964DD8F7DD6174A7629CAF86080826E2D00DAE94D6E7B0A49C5406872B161EDCF7316E7BEE30266616E1529F9968AC6A71B02263836244A3B1DA5C5B56A422E73A5DE0AA95D9FFF5653EAFD5FCC63F4E1616A3E9B3EBF971A360752C37F4885207B2791DF37C1D5BD8AB9309767F380B8361F529EED9E62CAECB2738767E1EE584F2CAEEC38A432936706462015BBB5D0CF57592CDBE0A83AA718A10638C02733F477DD768BC26AD5578A5BC93C64ED42EA7A49F2EF80906733A65B0458537224283A87EBC6D009639897F4D2CE2FD995085B78B7BDF7FA98D9F6CED252774C50B8B7AB8F7E414F68ECD60AC50C25429C04C62224D6CFA670222FC1156A7443535AA10DBE227D30B78EA0B6D18EEEF8247668B0C4A2D67C8F4476FDA80BBA7E7F0F6144B8439DDD495C1A5FD9D4A8B24AFC28B4F1716F1C0DF8E62A26AA1256B2B254B9B743EF4A9D9B122A5299B8A62A9E30AF33CF1A987FB8632B867EB0684248D78277D59676DFA64AB7497EEAE76DCDED571319F125E51248D5113D6BA7C77755B0E15D2A3891A2D7915A41EAF812016564B7E754A2496FB689A27908F8A7CA9BC4442F014916214E8E9E27C813D5A57A4A95268A4C605614281715C0719D6AF68B6EF8770293C43ED19CC57AA3444A41C21D31570C4714344948028D49A34080397642DCA9C8B87F68CE3D5F7CEE289D7C6F1C09E496C79F603BCF4DE241DB12FD6B8238C676DFEE7E828CBDF53FABC5CFAB863A895502B0A5D409D56486950502B0191A21086C60AB9A68EB4EBF2291C2D91306FCEE3B9D11A8E2C0017CC3C9E7F7F4E6DE6DA0609C4AE4612FD6CCF715CFFEC71DCF0E70338347A4EB1593EFF1E635763F482385446ABCB1ED5895AA4539A8429AC16CF5246E39CEC337CAD2E7B68BA157E182AF11864294CF3E85328D690EFC8F264011CF8601C4F1DBA80F5EBFB71A2B0849B5F3886C7B72FE1F84C117F7CBF80F67C13D52A8027270FB14B1E4887A2F888DF51AA54AA20CB3CE9EA60D63894EBECCFB59021AA27EADE4E22CC85094ECD95B1A5BD55B5B9DF1D9E82994E215BF7319073301DDAF8C1DEB3542483878626B6ED00356E28F5AE713DD8A16C56821DD735ED8997F7EFDC335AB9F3DAEDDBB0EFC373E8E2615ED07B147A755C49445B6355F482DED6EB58E1484B8C7062911B49AFE4BB652EF25503D0C87CC969A48C468A1011A272192B5776A2E5420137F466DED0CA9E97BDF9B19D73836B37BAB9BED578EBCC79A289199A86C20832A5D75C1C3222051E6D2B64B59C9DDAD98B8530253EF768CCA003BE62AF10096A1F092F88BCBFBB1383FC1270FED487D8F5E8ED97ABAF3023139F5C79EFEF771F58B3E6B254BEA71F01EB593614630D06D3638E40E5A9312FEB42DE570569D2A8D350950D435B6F9C28255211599FE55C4C0E94A63FC19F1EFCF2FD3B2E1F7C5A19AE53288E9C99EA7F6CD7DBBFE8686EDDA15B6EB3749ACF0E66AA7F8BF7649E0A7BA351A9C37D725146121525D5B3A44E39132D7F8933EB916F69C1C15BAF19FAE537B70E1F129BFF1560003A0DBCDD2406934B0000000049454E44AE426082,'image/png','<h3>Bientôt Intr@paris v2</h3>\r\n<p align=\"center\">\r\n<em>\r\n<a href=\"\">En savoir plus ...</a>\r\n</em>\r\n</p>',1,1,0,0);
