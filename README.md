# beware-of-phishing-sms-system

Moje założenia:
1.	Pominąłem dokładną implementację pola threadType – zakładam zero-jedynkowo, że URL jest bezpieczny lub nie (i na tej podstawie SMS jest blokowany lub nie). Nie mam realnego zewnętrznego serwisu – stąd taka decyzja.
2.	Z myślą o kosztach wywoływania zewnętrznego serwisu używam klasy UrlCache, która zapisuje wyniki zapytań z zewnętrznego serwisu w pamięci podręcznej (oczywiście realnie powinna tutaj być jakaś baza danych

Architektura:
1.	Przyjąłem rozwiązanie z bootcamps czyli rozdział odpowiedzialności pomiędzy różne klasy umieszczone w logicznych pakietach:
- config (pliki konfiguracyjne – w moim przypadku związane ze Spring)
- controller (zarządzanie endpointami REST)
- model (dane SMS)
- service (serwisy czyli klasy dedykowane to konkretnej logiki biznesowej: obsługa/przetwarzanie SMSów, obsługa subskrypcji, obsługa cache – szybszego i bezpłatnego pobierania wcześniejszych zapytań do zewnętrznego API, obsługa sprawdzania bezpieczeństwa URL w zewnętrznym API.

2.	Całość oparta na Spring – łatwe skalowanie, prosty kod i dużo potencjalnych usprawnień (różne komponenty)

