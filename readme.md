# Heroes of Might and Magic kötprog

## Windows megjegyzés:

A program futtatásához [Windows Terminal](https://www.microsoft.com/en-us/p/windows-terminal/9n0dx20hk701) használata erősen ajánlott. A cmd meg powershell nem feltétlen ismeri a terminál színezéséhez, kurzor manipulálásához használt ANSI kódokat.

Ezenkívül az ékezetes karakterek megjelenítéséhez szükséges lehet kiadni:
```
chcp 65001
```


Ha nagyon nem akar összeállni, a *linux.inf.u-szeged.hu*-n  lehet próbálkozni.

---
Belépési pont: *Managers/Game*
### Példa fordítás parancsra:
```
javac -cp src -encoding utf-8 -d out/build ./src/Managers/Game.java
```

### Futtatás:

```
 java -cp out/build Managers.Game 
```

**A terminál ablaknak legalább 120*30 méretűnek kell lennie, máskülönben baj lesz.** (A legtöbb terminál alkalmazás alapértelmezett mérete legalább ennyi)
# Irányítás
Mindig amikor menüpontok közül lehet választani, nem csak a kiírt sorszámokkal, hanem a menüpont szövegének első betűjével is ki lehet választani egy opciót (vagy első néhány betűjével, amennyiben nem egyértelmű a választás.) Ez nagy általánosságban hajlandó működni, csak az ékezetes betűknek kevésbé örül.

 A bemenet nem érzékeny a kis-és nagybetűkre. Amikor egy mezőre kíváncsi, a oszlopra betűvel (A-L), a sorra pedig számmal (0-9) lehet hivatkozni (pl. A5, d7).

 # Elrendezés
- Pálya fölött: fejléc. Mutatja az aktuális kört és a két játékfél hősének mannáját.

 - Pálya alatt: Eseményfal. Itt jegyződnek fel a különböző történések, mint egyes egységek lépései, elszenvedett sebzések, használt varázslatok.

 - Jobb felső sarok: Körsorrend. Megmutatja, melyik egység van éppen soron ( -> ), kik következnek utána és melyik egységnél kezdődik majd új kör.

 - Jobb oldal középen: Bemenet. Itt jelennek meg a különböző menük, ahol választani lehet az egység és hős cselekvések közül.


# Értékeléshez megjegyzések

Részletes javadoc dokumentált osztályok: *Units/Unit, Display*

Értelmes öröklődések: *Spells, Units*

Balanszolás: A villámcsapást nerfeltem mert olcsón mindent kinyírt egyből