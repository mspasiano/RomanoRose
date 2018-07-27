package com.vveye;

public class T2u {

    static {
        System.loadLibrary("t2u");
    }

    /**
     * Inizializzazione
     *
     * @param svraddr indirizzo del server o nome di dominio
     * @param svrport porta del server
     * @param svrkey  server key
     */
    public native static void Init(String svraddr, char svrport, String svrkey);

    /**
     * Imposta l'intervallo di porte P2P
     *
     * @param minport numero di porta minimo
     * @param maxport numero di porta massimo
     */
    public native static void SetPortRange(char minport, char maxport);

    /**
     * Aggiungi una porta di mappatura
     * Mappare la porta specificata del dispositivo remoto al locale
     *
     * @param uuid        device UUID (es. numero di serie del dispositivo)
     * @param remote_port porta di servizio del partner
     * @param local_port  esegue il mapping sulla porta locale, 0 significa utilizzare la porta casuale
     * @return > 0: mappa verso la porta locale
     * -1: impossibile creare la porta e la porta locale è occupata
     */
    public native static int AddPort(String uuid, char remote_port, char local_port);

    public native static int AddUdpPort(String uuid, char remote_port, char local_port);

    /**
     * Aggiungi una porta di mappatura
     * Utilizzare il dispositivo remoto come proxy per mappare localmente le porte di altri dispositivi di rete sul client
     *
     * @param uuid        device UUID (es. numero di serie del dispositivo)
     * @param remote_ip   IP dell'altro dispositivo nella stessa LAN del dispositivo
     * @param remote_port porta di servizio del partner
     * @param local_port  esegue il mapping sulla porta locale, 0 significa utilizzare la porta casuale
     * @return > 0: mappa verso la porta locale
     * -1: impossibile creare la porta e la porta locale è occupata
     */
    public native static int AddPortEx(String uuid, String remote_ip, char remote_port, char local_port);

    public native static int AddUdpPortEx(String uuid, String remote_ip, char remote_port, char local_port);

    /**
     * Aggiungi una porta di mappatura
     *
     * @param uuid        device UUID (es. numero di serie del dispositivo)
     * @param passwd      del dispositivo passwd
     * @param remote_ip   IP dell'altro dispositivo nella stessa LAN del dispositivo
     * @param remote_port porta di servizio del partner
     * @param local_port  esegue il mapping sulla porta locale, 0 significa utilizzare la porta casuale
     * @return > 0: mappa verso la porta locale
     * -1: impossibile creare la porta e la porta locale è occupata
     */
    public native static int AddPortV3(String uuid, String passwd, String remote_ip, char remote_port, char local_port);

    public native static int AddUdpPortV3(String uuid, String passwd, String remote_ip, char remote_port, char local_port);

    /**
     * Chiudi ed elimina la porta mappata
     *
     * @param port mappato sul numero di porta locale
     */
    public native static void DelPort(char port);

    public native static void DelUdpPort(char port);

    /**
     * Controllare se la porta mappata è già collegata al dispositivo remoto
     *
     * @param port mappato sul numero di porta locale
     * @return 1: connesso
     * 0: non connesso
     * -1: non esiste
     */
    public native static int PortStatus(char port);

    public native static int UdpPortStatus(char port);

    /**
     * Stato della porta del mapping di query
     *
     * @param port     mappato sul numero di porta locale
     * @param buff     Array BYTE contenente informazioni di connessione, formato JSON
     * @param buffsize La dimensione dell'array che contiene le informazioni di connessione
     * @return 1: connesso
     * 0: non connesso
     * -1: non esiste
     */
    public native static int PortStatusEx(char port, byte buff[], int buffsize);

    public native static int UdpPortStatusEx(char port, byte buff[], int buffsize);

    /**
     * Stato della connessione di query e server
     *
     * @return 1: connesso al server e lo stato è normale
     * 0: il server non è connesso
     * -1: SDK non inizializzato
     * -2: chiave server non valida
     */
    public native static int Status();

    /**
     * Controlla lo stato online del dispositivo
     *
     * @param uuid device UUID (es. numero di serie del dispositivo)
     * @return 1: dispositivo online
     * 0: il dispositivo non è online
     * -1: query fallita
     */
    public native static int Query(String uuid);

    /**
     * Richiedi lo stato online del dispositivo e i parametri aggiuntivi
     *
     * @param uuid     device UUID (es. numero di serie del dispositivo)
     *                 Parametri aggiuntivi del dispositivo di memorizzazione di array BYTE @ buff buff
     * @param buffsize dimensione dell'array per memorizzare parametri aggiuntivi del dispositivo
     * @param ipaddr   salva l'array IP BYTE della rete pubblica del dispositivo
     * @param ipsize   per salvare la dimensione dell'array dell'IP pubblico del dispositivo
     * @return 1: dispositivo online
     * 0: il dispositivo non è online
     * -1: query fallita
     */
    public native static int QueryEx(String uuid, byte buff[], int buffsize, byte ipaddr[], int ipsize);

    /**
     * Ricerca per trovare dispositivi locali
     *
     * @param result search result, convertito in stringa, un record per riga, formato: uuid = xxxx, ip = x.x.x.x
     * @return > = 0 Numero di dispositivi
     * -1: ricerca fallita
     */
    public native static int Search(byte result[]);

    /**
     * Cerca il dispositivo DVR nell'intranet dell'altro utente
     *
     * @param uuid   device UUID (es. numero di serie del dispositivo)
     * @param result Risultato della ricerca, convertito in stringa, un record per riga, nel formato: Codice produttore | Numero seriale DVR | Numero canale | IP | Porta
     * @return 1: la query ha avuto esito positivo con un dispositivo DVR
     * 0: la query ha avuto esito positivo ma nessun dispositivo
     * -1: connessione fallita
     * -2: query fallita
     * -3: la query è scaduta e l'altra parte non ha risposto
     */
    public native static int SearchDVR(String uuid, byte result[]);

    /**
     * Esci e rilascia risorse
     */
    public native static void Exit();
}
