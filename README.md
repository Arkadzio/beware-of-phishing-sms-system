# beware-of-phishing-sms-system

**Zadanie**
Hipotetyczny operator telekomunikacyjny dogadał się ze związkiem banków, że będzie zapobiegał otrzymywaniu phishingowych wiadomości tekstowych przez abonentów sieci,
którzy wyrazili na to chęć. Stwórz proste rozwiązanie, które będzie służyło temu celowi.

**Założenia**
Operator przechowuje SMSy w postaci JSON, przykładowy rekord ma postać:
{
"sender": "234100200300",
"recipient": "48700800999",
"message": "Dzień dobry. W związku z audytem nadzór finansowy w naszym banku proszą o potwierdzanie danych pod adresem: https://www.m-bonk.pl.ng/personal-data"
}

Wymyśl rozwiązanie, które będzie obsługiwać wszystkie SMSy, wykrywać phishing i odrzucać takie przypadki.
Do oceny czy URL wskazuje na stronę phishingową powinieneś użyć zewnętrznego serwisu dostępnego po HTTP. Serwis ten jest płatny za każde wywołanie. Możesz przyjąć, że serwis
wygląda tak:
POST /v1:evaluateUri HTTP/2
Host: web-risk-api-host.com
Content-Type: application/json
Authorization: Bearer test-1234567890
{
"uri": "https://testsafebrowsing.appspot.com/s/phishing.html"
}

i zwraca przykładową odpowiedź dla wykrytego zagrożenia:
{
"isSafe": false,
"threatType": "PHISHING"
}

threatType przyjmuje wartość: PHISHING, MALWARE lub SOCIAL_ENGINEERING
oraz poniższą odpowiedź dla bezpiecznego adresu URL:
{
"isSafe": true
}

Użytkownicy wyrażają chęć skorzystania lub rezygnacji z usługi przez wysłanie SMS o treści START lub STOP na określony numer.

**Wymagania techniczne**
Rozwiązanie należy stworzyć używając dowolnego języka JVM. Powinno składać się z publicznego repozytorium kodu źródłowego w serwisie GitHub.
Jeśli przyjąłeś jakieś dodatkowe założenia umieść je w pliku README. Jednym z ocenianych elementów, jest przyjęta architektura rozwiązania. Przy decyzji o wyborze architektury, załóż,
że masz pełną swobodę decyzji, nie jesteś ograniczony istniejącymi rozwiązaniami. Decyzje architektoniczne także opisz w README.
Operatorowi (jak zwykle) zależy na szybkim czasie wdrożenia i niskim koszcie obsługi. W razie wątpliwości w interpretacji wymagań przyjmij ograniczoną, prostszą funkcjonalność, ale
przedstaw rozwiązanie jak najbardziej gotowe do wdrożenia produkcyjnego.


**Moje założenia:**
1.	Pominąłem dokładną implementację pola threadType – zakładam zero-jedynkowo, że URL jest bezpieczny lub nie (i na tej podstawie SMS jest blokowany lub nie). Nie mam realnego zewnętrznego serwisu – stąd taka decyzja.
2.	Z myślą o kosztach wywoływania zewnętrznego serwisu używam klasy UrlCache, która zapisuje wyniki zapytań z zewnętrznego serwisu w pamięci podręcznej (w ulepszonej wersji programu jest do baza H2)

**Architektura:**
1.	Przyjąłem rozwiązanie z bootcamps czyli rozdział odpowiedzialności pomiędzy różne klasy umieszczone w logicznych pakietach:
- config (pliki konfiguracyjne – w moim przypadku związane ze Spring)
- controller (zarządzanie endpointami REST)
- model (dane SMS, subskrypcji, etc.)
- repository (interfejsy - repozytoria subskrypcji i url) 
- service (serwisy czyli klasy dedykowane to konkretnej logiki biznesowej: obsługa/przetwarzanie SMSów, obsługa subskrypcji, obsługa cache – szybszego i bezpłatnego pobierania wcześniejszych zapytań do zewnętrznego API, obsługa sprawdzania bezpieczeństwa URL w zewnętrznym API)

2.	Całość oparta na Spring – łatwe skalowanie, prosty kod i dużo potencjalnych usprawnień (różne komponenty)
