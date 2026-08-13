# Rapport qualité - TP1 SunuSanté

Nom / Groupe : Seydina Mouhamed Lybass Pouye - Mouhamadou Leye / Groupe 3

## 1. Qualité interne vs externe

Avant toute modification, `mvn test` était vert. Cela garantit-il une bonne
qualité interne ? Expliquez en une ou deux phrases, en citant les indices
concrets de la version de départ qui montrent un problème de qualité
interne malgré des tests verts.
Non. Des tests verts montrent que les fonctionnalités testées fonctionnent, mais ils ne garantissent pas la qualité interne du code. Dans la version initiale, une seule classe regroupe plusieurs responsabilités et la logique de tarification est dupliquée, ce qui nuit à la maintenabilité.

## 2. Complexité cyclomatique (avant / après)

| Méthode                | Complexité avant | Niveau de risque avant | Complexité après | Niveau de risque après |
| ---------------------- | ---------------: | ---------------------- | ---------------: | ---------------------- |
| `ajouterRendezVous`    |           **17** | **Modérée**            |                  |                        |
| `calculerTotalFacture` |           **14** | **Modérée**            |                  |                        |


## 3. Dette technique identifiée (matrice de Fowler)

Pour chacun des deux problèmes ci-dessous, classez-le dans la matrice
délibérée/involontaire × prudente/imprudente et justifiez en une phrase.

## 3. Dette technique identifiée (matrice de Fowler)

Pour chacun des deux problèmes ci-dessous, classez-le dans la matrice
délibérée/involontaire × prudente/imprudente et justifiez en une phrase.

| Problème | Délibérée ou involontaire ? | Prudente ou imprudente ? | Justification |
|----------|-----------------------------|--------------------------|---------------|
| Duplication de la logique de tarif | Involontaire | Imprudente | La même logique de tarification était recopiée dans deux méthodes, ce qui augmentait le risque d'incohérences et rendait les évolutions plus difficiles. |
| Absence de séparation affichage / logique métier | Involontaire | Imprudente | La classe `GestionRendezVous` regroupait plusieurs responsabilités, ce qui rendait le code plus difficile à maintenir et à faire évoluer. |

## 4. Cycle TDD

- Lien ou hash du commit RED : `f0d8a27`
- Lien ou hash du commit GREEN : `02faab5`
- Lien ou hash du commit REFACTOR : `1ce7e9c`
- Difficulté rencontrée pendant le cycle : Lors du refactoring, la logique du tarif dégressif a d'abord été appliquée dans `calculerTotalFacture`, ce qui a cassé un test de caractérisation. Il a fallu conserver le comportement existant de cette méthode tout en centralisant la logique de calcul du tarif.

## 5. Couverture de code

- Couverture globale obtenue : **85 %**
- Classe la moins couverte : **RendezVousAffichage (36 %)**, ce taux est justifié car cette classe est principalement consacrée à l'affichage des rendez-vous et ne contient pas de logique métier importante. La faible couverture de cette classe ne signifie donc pas nécessairement un problème de qualité.

Après l'exécution de mvn test, la couverture obtenue avec JaCoCo est de 85 % des instructions et 69 % des branches.
Les principales classes de logique métier présentent une bonne couverture : GestionRendezVous atteint 86 %, RendezVousRepository 94 %, CalculateurTarif 100 % et TypeConsultation 100 %. La classe RendezVousAffichage est moins couverte (36 %) car elle concerne principalement l'affichage.

Une classe à 0 % n'est pas forcément un problème : la couverture est une métrique qui indique quelle partie du code est exécutée par les tests, mais une couverture élevée ne garantit pas l'absence de bugs. Un test doit également contenir des assertions pertinentes. L'objectif de 70–80 % sur le code métier critique est donc atteint.

## 6. Positionnement ISO/IEC 25010

Pour au moins 3 des 8 caractéristiques, indiquez en une phrase si le
refactoring les a améliorées, et comment.

| Caractéristique | Impact du refactoring |
|---|---|
| Maintenabilité | Le refactoring a amélioré la maintenabilité en séparant les responsabilités dans plusieurs classes, ce qui facilite la compréhension, la modification et l'évolution du code. |
| Fiabilité | Le refactoring a amélioré la fiabilité en conservant les comportements existants grâce aux tests et en centralisant les règles de calcul des tarifs. |
| Modularité | Le refactoring a amélioré la modularité en séparant le calcul des tarifs, le stockage, l'affichage et l'orchestration dans des classes distinctes. |

## 7. Ce que je referais différemment

Si c'était à refaire, je commencerais plus tôt par identifier les différentes
responsabilités de la classe `GestionRendezVous` afin d'éviter qu'elle devienne
trop volumineuse. Je mettrais également en place davantage de tests dès le
début, notamment pour les cas limites et les différentes règles de tarification.
Cela aurait permis de réaliser les refactorings plus rapidement et avec
davantage de confiance.