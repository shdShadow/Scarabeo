# Scarabeo
![Screenshot-Overview](https://github.com/shdShadow/Scarabeo/assets/112032211/c38a128f-6195-4781-aa10-9e9abec99171)

## Introduzione
Questa e' una ricostruzione del gioco da tavolo "Scarabeo" scritta in Java e leggermente rivisitata.
Il gioco viene gestito da un server il quale risponde a seguito dei messaggi inviati dai client. Il limite minimo e massimo di giocatori, pertanto di client, in questo momento e' limitato a 2.
## Installazione
### Tramite CLI
```
git clone https://github.com/shdShadow/Scarabeo
cd Scarabeo\
code .
```
E' possibile utilizzare qualsiasi editor di codice, l'importante e' che il progetto venga aperto alla sua root e non nella cartella src\
### Da github
- Fai click sul pulsante "<> Code"
- Seleziona dal menu la voce "Download Zip"
- Estrai l'archivio in una posizione a piacere
- Apri il progetto tramite l'editor che preferisci dalla sua root e non dalla cartella src\
## Avvio
- Avvia un istanza del server eseguendo il file ` server.java `
- Avvia due istanze di client eseguendo il file ` client.java ` 
- Divertiti!
## Regole
Alcune regole vengono direttamente dal gioco originale, altre invece sono state modificate per rendere il gioco piu' rapido e piu' facile da implementare
- La prima parola della partita puo' essere scritta in qualsiasi parte dal tabellone e non per forza al centro
- Tutte le parole a partire dalla seconda devono per forza intersecarsi con almeno una lettera di un altra parola gia' presente sul tabellone
- La parole possone essere scritte in verticale, orizzantale in ordine corretto o inverso ma non possono essere scritte in diagonale
- La partita finisce al raggiungimento di un punteggio massimo (Impostato a 20 di default).
## Tasti e comandi 
- Arrow Keys -> Movimento all'interno del campo di gioco
- Enter      -> Invio parola al server
- Left_Shift -> Cambio mano e passo il turno
- Esc        -> Annullamento della giocata attuale
## Personalizzazione
Alcuni settaggi come il punteggio massimo, la dimensione delle caselle disegnate,... sono modificabili tramite il file settings.java
## Future implementazioni
- Miglioramenti alla grafica di vincita/perdita
- Miglioramenti al sistema di chiusura delle risorse e del programma
- Implementazione di un controllo per parole multiple.
- Implementazione di controllo di parola gia' scritta
- Implementazione di controllo automatico di estensione di una parola gia' presente. Esempio:
    ```
          M                            M
          A                            A
          T          ---->             T    
    G A T T O                    G A T T O
          O                            O
                                       N
                                       E
    ```
    Al momento per scrivere la parola 'mattone' e' necessario riscrivere la parola per intero, compreso quindi 'matto'

