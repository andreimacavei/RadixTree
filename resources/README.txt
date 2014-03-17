Tema 1 - Programare Orientata pe Obiecte

Student: Macavei Andrei-Gabriel 
Grupa: 324 CB

Enunt:
Se cere implementarea unui Radix Tree pentru indexarea cuvintelor dintr-un text.

Rezolvare:

Am implementat o structura radix tree folosind doua clase:

1) RadixTreeNode - clasa ce reprezinta un nod din arbore. Fiecare nod are o lista de referinte la nodurile copii ai acestuia.

Clasa are ca membrii :
-prefix - prefixul nodului respectiv
-isTerminal - o valoare boolean care arata daca nodul are asociata sau nu o pozitie
-position - un TreeSet ce contine toate pozitiile (indecsii) unde apare prefixul asociat. Acesti indecsi sunt sortati in ordine crescatoare
-childNodes - o lista cu referinte la copii acestui nod in arbore.

Clasa are mai multi constructori pentru a usura creearea unui nod nou fie folsind
doar prefixul, sau prefixul cu pozitia lui ori cu o lista de pozitii.

Pe langa acesti membrii clasa are mai si metode "getters and setters" si implementeaza interfata Iterable pentru a itera mai usor prin copii nodului curent. Suprascrie metoda iterator() ce intoarce un iterator pt lista de noduri copii.

2) RadixTree - clasa ce reprezinta arborele (contine radacina arborelui) si implementeaza metodele de inserare si cautare a indecsilor in arborele curent.

Elementele clasei sunt:
-root - radacina arborelui , de tip RadixTreeNode
-constructorul default ce initializeaza radacina cu sirul vid ""

-Metoda longestPrefix - returneaza cel mai lung prefix avut in comun de doua siruri
-Metoda indexPrefix - metoda ce construiest arborele propriu zis, pe baza textului din fisier. 
Metoda trateaza urmatoarele cazuri:

I) Daca prefixul ce se doreste a fi inserat este un duplicat al prefixului unui nod existent, se adauga pozitia la lista de pozitii a nodului din urma.

II) Daca prefixul de inserat este mai mare ca prefixul nodului curent, atunci cauta un copil care sa aiba in continuare acelasi prefix cu cel cautat.
Daca nu gasim atunci adaugam un nod nou la copii nodului curent.

III) Daca prefixul de inserat si prefixul nodului curent au in comun o parte doar, atunci:
-se creeaza un nod nou care are prefixul egal cu prefixul nodului curent mai putin partea comuna cu prefixul nodului de inserat.
-se muta copii nodului curent la nodul nou creat, si se sterg pt nodul curent
-se adauga nodul nou creat ca fiind copil al nodului curent
-se creeaza un nou nod cu prefixul egal cu prefixul dorit de inserat mai putin partea comuna cu nodul curent si se adauga la copii nodului curent.

-Metoda getAllPosition - metoda care cauta si returneaza in caz ca gaseste, toate pozitiile pe care apare un prefix.


Problemele intampinate au fost la partea de cautare facuta recursiv, deoarece sunt multe cazuri posibile si a trebuit sa le testez pentru fiecare exemplu in parte.
Pentru aceasta am folosit foarte mult debugger-ul si mi-a fost foarte de ajutor.
O problema importanta a fost cea cand prefixul pe care vroiam sa-l gasesc era egal cu cel al nodului curent si trebuia sa returnez atat pozitia acestuia,
cat si toate pozitiile nodurilor copii, pt ca prefixul facea parte si din acele cuvinte.


