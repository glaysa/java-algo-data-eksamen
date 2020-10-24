# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Vi har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1:  ``leggInn(T verdi)`` - Oppgaven er løst ved å kopiere koden fra kompendiet 5.3 3a, men i tillegg har gjort endringene som trengs for at referanse
             'forelder' får riktig verdi.
             
* Oppgave 2: ``antall(T verdi)`` - Oppgaven er løst ved bruk av en while loop som itererer gjennom treet og hver gang den finner verdien som er lik den gitt verdien øker 'antall' variablen og til slutt blir den returnert. I starten returnerer den ikke riktig resultat fordi
             jeg trodde at duplikater blir legget inn som venstre barn liksom i kompendiet, ut fra leggInn(T) metoden fant jeg ut at i dette
             tilfelle blir duplikater lagret som høyre barn. Til slutte passerte den alle testene. 
             
* Oppgave 3a: ``førstePostorden(Node p)`` - Metoden er implementert ved bruk av rekursjon - den kaller på seg selv. Det er if-else setninger som passer på om den skal stoppe rekursjonen eller kjøre videre. Hvis `p.venstre` eller `p.høyre` er ikke null skal den kalle på seg selv. Hvis `p.venstre` og `p.høyre` er null stopper rekursjonen og returnerer verdien til p.
             
* Oppgave 3b: ``nestePostorden(Node p)`` - Metoden er implementert ved bruk av if-else setninger som kjører etter disse tilfellene:
    1. `p.forelder` er `null` - hvis p sin foreldre er null betyr det at treet inneholder 0 eller en node.
    2. `p.forelder.høyre` er lik `p` - hvis p er høyre barn betyr det at neste noden i postorden blir p sin foreldre.
    3. `p.forelder.høyre` er lik `null` - hvis p er venstre barn uten en subtre og sin foreldre har ikke en høyre barn, neste noden i postorden blir `p.forelder`.
    4. Hvis ingen av de er sant, kjører else-blokken som kaller på `førstePostorden(Node T)` - hvis p er venstre barn med en subtre må vi finne sin førstePostorden noden.
		
* Oppgave 4a: `postorden(Oppgave o)` - Metoden er implementert ved hjelp av `førstePostorden(Node p)` og `nestePostorden(Node p)`. Først finner vi rot noden i en postorden rekkefølge ved hjelp av `færstePostorden(Node p)` og bruker en while-loop til å traversere gjennom treet ved hjelp av `nestePostorden(Node p)`. I while-loopen kaller vi `Oppgave o` til å printe ut verdien til hver node.

* Oppgave 4b: `postordenRekurive(Node p, Oppgave o)` - Metoden er implementert ved bruk av rekursjon. Først kaller den på seg selv så lenge `p.venstre` ikke er null. Etterpå kaller den på seg selv igjen så lenge `p.høyre` ikke er null. Den skal sjekke alltid om `p.høyre` har et venstre barn fordi rekursjonen som sjekker venstre barn skjer først. Ved hjelp av `Oppgave o` printer den ut verdien til hver node.

* Oppgave 5a: `serialize()` - Metoden er implementert ved hjelp av en kø. Først legger vi inn rot noden i køen. I en while-loop sjekker vi om køen er tomt. Så lenge det ikke er tomt, skal vi fjerne verdiene ved hjelp av `kø.poll()`. I while-loopen lagrer vi fjernet verdi en variable `Node n` og i en arraylist som skal returneres. I tillegg sjekker vi i while-loopen om `Node n` har et venstre eller høyre barn. Om barna finnes blir de legget inn i køen og da fortsetter while-loopen til den har gått gjennom hele treet. Til slutt blir arraylisten returnert.

* Oppgave 5b: `deserialize(Arraylist list, Comparator c)` - Den er implementert ved hjelp av `leggInn(verdi)` metoden. Først opprettet jeg et tre objekt. Med en for loop itererte jeg gjennom arraylisten hvor hver element blir legget inn i treet ved hjelp av `tre.leggInn(element)`. Til slutt returnerte jeg tre objektet. 

* Oppgave 6a: `fjern(T verdi)` -  Metoden er implementert ved å kopiere koden fra kompendiet 5.2 8d, men i tillegg har gjort endringene som trengs for at referanse
             'forelder' får riktig verdi.
	 
* Oppgave 6b: `fjernAlle(T verdi)` - Metoden er implementert ved hjelp av `antall(T verdi)` og `fjern(T verdi)`. Først deklarerte jeg en teller som passer på hvor mange forekomster av gitt verdien blir fjernet. I en while-loop sjekket jeg at så lenge antall av gitt verdien er større enn 0 skal den fjerne verdien ved hjelp av `fjern(verdi)` og samtidig øker jeg teller variablen. `fjern(T verdi)` metoden mynker antall av gitt verdien derfor jeg trengte ikke å oppdatere den i while-loopen. Til slutt returnerte jeg teller variablen. 

* Oppgave 6c: `nullstill()` - Metoden er løst ved å traversere i postorden rekkefølge gjennom treet ved hjelp av metodene `førstePostorden(Node p)` og `nestePostorden(Node p)` og satt verdiene til hver node til null. `færstPostorden(Node p)` returnert rot noden til treet. I en while-loopen setter jeg nodens verdi til null og går til neste node ved hjelp av `nestePostorden(Node p)`. Treets antall blir rudusert også i while-loopen. Til slutt satt jeg rot lik null. 
