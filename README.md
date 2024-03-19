# Úkol eppTec
_V tomto repozitáři můžete najít mnou vypracované zkušební zadání pro eppTec._

### Zadaní

```
a) Algoritmus
 Prosím napište algoritmus (jazyk volný), který ze seznamu vytřídí prvky podle nějakých pravidel,
 včetně příkladu takového pravidla. Tzn. ať je tam sekce, kam se doplní konkrétní pravidla, 
 prvky budou někde mimo v seznamu, který algoritmus projde a smaže hodnoty, které neprošly
 pravidly.

b)  Datový model
    1) Vytvořte jednoduchý datový model obsahující 4 základní entity: Klient, Účet, Transakce a Balance.
    2) Naznačte základní sadu atributů v jednotlivých tabulkách, kardinalitu, primární/cizí klíče, apod.
    3) V tabulce transakcí se bude vyskytovat TYP_TRANSAKCE, který bude odkazovat do číselníku typů transakcí.
    4) Předpokládejte, že tabulka BALANCE obsahuje denní snímky nesoucí informaci o výši jednotlivých komponent pohledávky (jistina, úrok, poplatky) na konci dne.
    5) Postavte dotaz, který vybere všechny klienty (např. id_klient, jméno a příjmení) pro něž bude platit, že suma jistin všech jejich účtů na konci měsíce bude větší než číslo c.
    6) Postavte dotaz, který zobrazí 10 klientů s maximální celkovou výší pohledávky (suma všech pohledávek klienta) k ultimu měsíce a tuto na konci řádku vždy zobrazte.
```
##
## Algoritmus


Rozhodl jsem se použít Javu jako programovací jazyk, protože s ním mám největší zkušenost a cítím se v něm nejkomfortněji.
Vytvořil jsem algoritmus, který má za úkol filtrovat všechna slova v seznamu a vybírat pouze palindromy, tj. slova, která 
se čtou stejně zleva doprava i zprava doleva (např. ANNA). Pro tento účel jsem vytvořil jednoduchý Java soubor s názvem Main, 
ve kterém je tento algoritmus implementován.

```JAVA
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("Anna");
        words.add("Hannah");
        words.add("Mom");
        words.add("Apple");
        words.add("DefinitelyNotPalindrome");
        words.add("noon");
        words.add("lEVeL");

        System.out.println(words.toString());
        System.out.println("--------------------");
        System.out.println(palindromesOnly(words).toString());

    }

    private static List<String> palindromesOnly(List<String> words) {
        List<String> palindromes = new ArrayList<>();
        for (String word : words) {
            if (isPalindrome(word)) {
                palindromes.add(word);
            }
        }
        return palindromes;
    }

    private static boolean isPalindrome(String word){
        String lowerCaseWord = word.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i = lowerCaseWord.length() - 1; i >= 0; i--) {
            sb.append(lowerCaseWord.charAt(i));
        }
        return sb.toString().equals(lowerCaseWord);
    }

}
```
##### Konzole
```
[Anna, Hannah, Mom, Apple, DefinitelyNotPalindrome, noon, lEVeL]
--------------------
[Anna, Hannah, Mom, noon, lEVeL]
```
Seznam slov je reprezentován jako __List<String>__, ze kterého jsou vybrána palindromní slova pomocí metody _.palindromesOnly()_.
Tato metoda prochází celým seznamem a pomocí metody _.isPalindrome()_ určuje, zda je slovo palindrom nebo ne.
Palindromní slova jsou následně přidána do nového List<String>, který je poté vrácen.
Metoda isPalindrome není citlivá na velikost písmen.
##
## Datový model
Předtím, než jsem začal psát SQL queery, vytvořil jsem si pro sebe menší strukturu databáze a jak by měla vypadat.
####
Rozhodl jsem se zachovat názvy tabulek v češtině, jak bylo uvedeno v zadání.
## 1.) 
    1) Vytvořte jednoduchý datový model obsahující 4 základní entity: Klient, Účet, Transakce a Balance.
    2) Naznačte základní sadu atributů v jednotlivých tabulkách, kardinalitu, primární/cizí klíče, apod.
    3) V tabulce transakcí se bude vyskytovat TYP_TRANSAKCE, který bude odkazovat do číselníku typů transakcí.
    4) Předpokládejte, že tabulka BALANCE obsahuje denní snímky nesoucí informaci o výši jednotlivých komponent pohledávky (jistina, úrok, poplatky) na konci dne.
##
```SQL
CREATE TABLE KLIENT(
    id_klient INT PRIMARY KEY,
    jmeno VARCHAR(225),
    prijmeni VARCHAR(225)
);
```
```SQL
CREATE TABLE UCET(
    id_ucet INT PRIMARY KEY,
    id_klient INT,
    FOREIGN KEY (id_ucet) REFERENCES KLIENT(id_klient)
);
```

```SQL
CREATE TABLE TRANSAKCE(
    id_transakce INT PRIMARY KEY,
    id_ucet INT,
    typ_transakce INT,
    FOREIGN KEY (id_ucet) REFERENCES UCET(id_ucet),
    FOREIGN KEY (typ_transakce) REFERENCES TYP_TRANSAKCE(id_typ)
);
```

```SQL
CREATE TABLE TYP_TRANSAKCE(
    id_typ INT PRIMARY KEY,
    nazev VARCHAR(225),
    FOREIGN KEY (id_ucet) REFERENCES UCET(id_ucet)
);
```

```SQL
CREATE TABLE BALANCE(
    id_balance INT PRIMARY KEY,
    id_ucet INT,
    datum DATE,
    jistina DECIMAL,
    poplatky DECIMAL,
    FOREIGN KEY (id_ucet) REFERENCES UCET(id_ucet)
);
```
## 2.)
    5) Postavte dotaz, který vybere všechny klienty (např. id_klient, jméno a příjmení) pro něž bude platit, že suma jistin všech jejich účtů na konci měsíce bude větší než číslo c.
###
Nebyl jsem si úplně jistý, co přesně znamená číslo "c", ale 
pokud bude potřeba změnit částku, podle které se klienti vyhledávají, pouze stačí nahradit "c" za danou částku.
```SQL
SELECT KLIENT.id_klient, KLIENT.jmeno, KLIENT.prijmeni
FROM KLIENT
JOIN UCET ON Klient.id_klient = UCET.id_klient
JOIN BALANCE ON Ucet.id_ucet = BALANCE.id_ucet
WHERE MONTH(BALANCE.datum) = MONTH(CURRENT_DATE())
GROUP BY KLIENT.id_klient, KLIENT.jmeno, KLIENT.prijmeni
HAVING SUM(BALANCE.jistina) > c;
```
## 3.)
    6) Postavte dotaz, který zobrazí 10 klientů s maximální celkovou výší pohledávky (suma všech pohledávek klienta) k ultimu měsíce a tuto na konci řádku vždy zobrazte.
###
Myslím si, že v zadaní byl překlep u slova "ultimu měsicu", tak jsem odvodil, že se mluvilo o minulém měsíci.
```SQL
SELECT KLIENT.id_klient, KLIENT.jmeno, KLIENT.prijmeni,
SUM(BALANCE.jistina) AS CELKOVA_POHLEDAVKA
FROM KLIENT
JOIN UCET ON KLIENT.id_klient = UCET.id_klient
JOIN Balance ON UCET.id_ucet = BALANCE.id_ucet
WHERE MONTH(BALANCE.datum) = MONTH(CURRENT_DATE())
GROUP BY KLIENT.id_klient, KLIENT.jmeno, KLIENT.prijmeni
ORDER BY CELKOVA_POHLEDAVKA DESC
LIMIT 10;
```



