# Planetarium
 
Esercizio 1 del progetto Arnaldo.

	Tutti i diritti del progetto sono del team: The Tesler Team, semplicemente TTT.

Il progetto è pubblico, la copia del codice è concessa se e solo se vengono dati i crediti al team TTT.<br>
Viene inoltre reso disponibile il file UML "Diagramma UML.pdf" che contiene sia come viene gestito l'output e l'input della console (pagina 1) sia come le classi del programma interagiscono tra loro (pagina 2). La parte che risolve il quesito è pensata come una libreria, perciò le classi della gestione input e output sono separate.<br>

<ul>
Il programma è completo delle seguenti funzionalità richieste:
  <li>Gestione dei corpi celesti del sistema stellare:
		<ul>
			<li> Aggiunta di nuovi pianeti o lune, in caso di nuove scoperte.	</li>
			<li> Rimozione di vecchi pianeti o lune, in caso di “catastrofi naturali”.	</li>
			<li> Identificazione di ciascun corpo celeste con un codice univoco.	</li>
		</ul>
	</li>
	<li>Ricerca di un corpo celeste all’interno del sistema:
		<ul>
			<li> Possibilità di capire se è presente nel sistema stellare.</li>
			<li> Nel caso di lune, identificazione del pianeta attorno a cui gira.</li>
		</ul>
	</li>
	<li> Visualizzazione delle informazioni:
		<ul>
			<li> Dato un pianeta, visualizzazione delle lune che gli orbitano intorno.</li>
			<li> Data una luna, visualizzazione del percorso [stella > pianeta > luna] necessario per raggiungerla.</li>
		</ul>
	</li>
	<li> Calcolo del centro di massa su richiesta, sulla base delle informazioni disponibili volta per volta.</li>
	<li> Calcolo della rotta fra due corpi celesti:
		<ul>
			<li> Su richiesta dell’utente, il programma deve mostrare la rotta fra due corpi del sistema stellare selezionati dall’utente stesso.</li>
			<li> Le leggi imperiali impongono che, nel caso in cui si viaggi fra due corpi di uguale “grado” (due lune, o due pianeti), si faccia scalo sul corpo celeste di grado più alto (rispettivamente, il pianeta in comune o la stella del sistema). In questo modo, la rotta fra due corpi celesti qualunque è unica, e l’Impero riesce a mantenere l’ordine.</li>
			<li> La rotta deve essere rappresentata come sequenza di corpi celesti (ad esempio: Luna 1 > Pianeta 1 > Stella > Pianeta 2).</li>
			<li> Deve essere indicata la distanza totale che si percorrerebbe seguendo la sequenza sopra indicata (nell’esempio precedente: 8,65).</li>
		</ul>
	</li>
	<li> Calcolo della collisione fra i corpi celesti:
		<ul>
			<li> Su richiesta dell’utente, il programma deve stabilire se sia possibile che due corpi qualunque del sistema collidano uno contro l’altro. Non è necessario stabilire quando e dove, né quali corpi siano, ma solamente se questo possa succedere.
			<li> Due corpi possono collidere se e solo se esiste una configurazione del sistema per cui due corpi possono trovarsi nella stessa posizione puntuale.
			<li> Ogni corpo ha distanza fissa dal corpo attorno a cui ruota (detta raggio di rivoluzione). Pertanto, nessuna luna colliderà mai col proprio pianeta e nessun pianeta colliderà mai con la stella del sistema. </li>
		</ul>
	</li>
</ul>

Riassunto: ci sono tutte le funzioni, sia quelle base, sia quelle aggiuntive 1 che aggiuntive 2.

Il programma include una piccola sezione di aiuto accessibile direttamente dal menù della console.
Sono stati aggiunti Easter-Eggs.
