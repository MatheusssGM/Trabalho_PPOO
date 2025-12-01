# Diagrama UML Completo - Simulação Predador-Presa

## Legenda de Relacionamentos UML:
- **→** Dependência (uses) - linha tracejada com seta aberta
- **◇** Agregação (has-a) - linha sólida com losango vazio
- **◆** Composição (owns-a) - linha sólida com losango preenchido
- **△** Herança/Generalização (is-a) - linha sólida com seta triangular vazia
- **◁** Implementação de Interface - linha tracejada com seta triangular vazia
- **[0..1]**, **[1]**, **[1..*]**, **[*]** - Multiplicidades (cardinalidades)

## Visibilidade dos Membros:
- **+** public
- **#** protected
- **-** private
- **~** package
- **{static}** método/atributo estático
- **{abstract}** método/classe abstrata

```plantuml
@startuml SimuladorPredadorPresaCompleto

!theme plain
scale 0.9
skinparam classAttributeIconSize 0
skinparam defaultFontSize 10

' ====================== INTERFACES ======================
interface Simulable {
    +act(currentField: Campo, updatedField: Campo, newAnimals: List<Animal>): void
    +isAlive(): boolean
}

interface Predator {
    +hunt(field: Campo, location: Location): Location
    +getFoodLevel(): int
    +isHungry(): boolean
}

interface Prey {
    +escape(field: Campo, location: Location): Location
    +detectsPredators(field: Campo, location: Location): boolean
}

interface ConfigurationProvider {
    +getFieldWidth(): int
    +getFieldHeight(): int
    +getRabbitProbability(): double
    +getFoxProbability(): double
    +getLionProbability(): double
    +getHumanProbability(): double
    +getSimulationSteps(): int
    +displayConfiguration(): void
}

interface AnimalFactory {
    +createAnimal(probability: double, config: ConfigurationProvider): Animal
    +canPlace(animal: Animal, environment: Environment): boolean
}

interface EnvironmentManager {
    +populateEnvironments(field: Campo): void
    +getEnvironmentForPosition(row: int, col: int, fieldDepth: int, fieldWidth: int): Environment
}

interface ViewManager {
    +setupColors(): void
    +showStatus(step: int, field: Campo): void
    +isViable(field: Campo): boolean
    +setAnimalColor(animalClass: Class<?>, color: Color): void
}

' ====================== CLASSES ABSTRATAS ======================
abstract class Animal {
    #{static} rand: Random
    #age: int
    #alive: boolean
    #location: Location
    
    +Animal(randomAge: boolean)
    +{abstract} act(currentField: Campo, updatedField: Campo, newAnimals: List<Animal>): void
    #incrementAge(): void
    #breed(): int
    #canBreed(): boolean
    #{abstract} createOffspring(randomAge: boolean): Animal
    #{abstract} getMaxAge(): int
    #{abstract} getBreedingAge(): int
    #{abstract} getBreedingProbability(): double
    #{abstract} getMaxLitterSize(): int
    +isAlive(): boolean
    +setDead(): void
    +setLocation(newLocation: Location): void
    +setLocation(row: int, col: int): void
    +getLocation(): Location
    +getAge(): int
}

abstract class Environment {
    +{abstract} canEnter(animal: Animal): boolean
    +{abstract} toString(): String
}

' ====================== CLASSES CONCRETAS - ANIMAIS ======================
class Rabbit {
    -{static} BREEDING_AGE: int = 5
    -{static} MAX_AGE: int = 50
    -{static} BREEDING_PROBABILITY: double = 0.30
    -{static} MAX_LITTER_SIZE: int = 6
    
    +Rabbit(randomAge: boolean)
    +act(currentField: Campo, updatedField: Campo, newAnimals: List<Animal>): void
    +detectsPredators(field: Campo, location: Location): boolean
    +escape(field: Campo, location: Location): Location
    #createOffspring(randomAge: boolean): Animal
    #getMaxAge(): int
    #getBreedingAge(): int
    #getBreedingProbability(): double
    #getMaxLitterSize(): int
}

class Fox {
    -{static} BREEDING_AGE: int = 8
    -{static} MAX_AGE: int = 150
    -{static} BREEDING_PROBABILITY: double = 0.18
    -{static} MAX_LITTER_SIZE: int = 3
    -{static} INITIAL_FOOD_LEVEL: int = 20
    -{static} HUNGER_LOSS: int = 1
    -{static} RABBIT_FOOD_VALUE: int = 12
    -foodLevel: int
    
    +Fox(randomAge: boolean)
    +act(currentField: Campo, updatedField: Campo, newAnimals: List<Animal>): void
    +hunt(field: Campo, location: Location): Location
    -incrementHunger(): void
    +getFoodLevel(): int
    +isHungry(): boolean
    #createOffspring(randomAge: boolean): Animal
    #getMaxAge(): int
    #getBreedingAge(): int
    #getBreedingProbability(): double
    #getMaxLitterSize(): int
}

class Lion {
    -{static} BREEDING_AGE: int = 18
    -{static} MAX_AGE: int = 180
    -{static} BREEDING_PROBABILITY: double = 0.10
    -{static} MAX_LITTER_SIZE: int = 2
    -{static} INITIAL_FOOD_LEVEL: int = 60
    -{static} HUNGER_LOSS: int = 1
    -{static} FOX_FOOD_VALUE: int = 14
    -{static} RABBIT_FOOD_VALUE: int = 6
    -foodLevel: int
    
    +Lion(randomAge: boolean)
    +act(currentField: Campo, updatedField: Campo, newAnimals: List<Animal>): void
    +hunt(field: Campo, location: Location): Location
    -incrementHunger(): void
    +getFoodLevel(): int
    +isHungry(): boolean
    #createOffspring(randomAge: boolean): Animal
    #getMaxAge(): int
    #getBreedingAge(): int
    #getBreedingProbability(): double
    #getMaxLitterSize(): int
}

class Human {
    -{static} BREEDING_AGE: int = 20
    -{static} MAX_AGE: int = 180
    -{static} BREEDING_PROBABILITY: double = 0.10
    -{static} MAX_LITTER_SIZE: int = 3
    -{static} LION_FOOD_VALUE: int = 28
    -foodLevel: int
    
    +Human(randomAge: boolean)
    +act(currentField: Campo, updatedField: Campo, newAnimals: List<Animal>): void
    +hunt(field: Campo, location: Location): Location
    -incrementHunger(): void
    +getFoodLevel(): int
    +isHungry(): boolean
    #createOffspring(randomAge: boolean): Animal
    #getMaxAge(): int
    #getBreedingAge(): int
    #getBreedingProbability(): double
    #getMaxLitterSize(): int
}

' ====================== CLASSES CONCRETAS - AMBIENTES ======================
class Mountain {
    +canEnter(animal: Animal): boolean
    +toString(): String
}

class Savanna {
    +canEnter(animal: Animal): boolean
    +toString(): String
}

class Plains {
    +canEnter(animal: Animal): boolean
    +toString(): String
}

class Burrow {
    +canEnter(animal: Animal): boolean
    +toString(): String
}

' ====================== CLASSES UTILITÁRIAS ======================
class Location {
    -row: int
    -col: int
    
    +Location(row: int, col: int)
    +equals(obj: Object): boolean
    +toString(): String
    +hashCode(): int
    +getRow(): int
    +getCol(): int
}

class Counter {
    -name: String
    -count: int
    
    +Counter(name: String)
    +getName(): String
    +getCount(): int
    +increment(): void
    +reset(): void
}

class Campo {
    -depth: int
    -width: int
    -field: Object[][]
    -environments: Environment[][]
    -rand: Random
    
    +Campo(depth: int, width: int)
    +clear(): void
    +place(animal: Animal, row: int, col: int): void
    +place(animal: Animal, loc: Location): void
    +getObjectAt(loc: Location): Animal
    +getObjectAt(row: int, col: int): Animal
    +getDepth(): int
    +getWidth(): int
    +setEnvironmentAt(row: int, col: int, env: Environment): void
    +getEnvironment(row: int, col: int): Environment
    +getEnvironment(loc: Location): Environment
    +adjacentLocations(location: Location): Iterator<Location>
    +adjacentLocationsList(location: Location): List<Location>
    +freeAdjacentLocation(location: Location): Location
    +randomAdjacentLocation(location: Location): Location
}

class EstatisticasCampo {
    -counters: Map<Class<?>, Counter>
    -countsValid: boolean
    
    +EstatisticasCampo()
    +getPopulationDetails(field: Campo): String
    +reset(): void
    +incrementCount(animalClass: Class<?>): void
    +countFinished(): void
    +isViable(field: Campo): boolean
    -generateCounts(field: Campo): void
}

' ====================== IMPLEMENTAÇÕES CONCRETAS ======================
class ConfigurationManager {
    -{static} CONFIG_FILE: String = "config.txt"
    -{static} DEFAULT_FIELD_WIDTH: String = "100"
    -{static} DEFAULT_FIELD_HEIGHT: String = "80" 
    -{static} DEFAULT_RABBIT_PROBABILITY: String = "0.08"
    -{static} DEFAULT_FOX_PROBABILITY: String = "0.02"
    -{static} DEFAULT_LION_PROBABILITY: String = "0.008"
    -{static} DEFAULT_HUMAN_PROBABILITY: String = "0.001"
    -{static} DEFAULT_SIMULATION_STEPS: String = "500"
    -config: Properties
    
    +ConfigurationManager()
    +loadConfiguration(): void
    -setDefaultValues(): void
    +saveConfiguration(): void
    +createExampleConfigFile(): void
    +getFieldWidth(): int
    +getFieldHeight(): int
    +getRabbitProbability(): double
    +getFoxProbability(): double
    +getLionProbability(): double
    +getHumanProbability(): double
    +getSimulationSteps(): int
    +displayConfiguration(): void
    +setFieldWidth(width: int): void
    +setFieldHeight(height: int): void
    +setRabbitProbability(prob: double): void
    +setFoxProbability(prob: double): void
    +setLionProbability(prob: double): void
    +setHumanProbability(prob: double): void
    +setSimulationSteps(steps: int): void
}

class ConcreteAnimalFactory {
    +createAnimal(probability: double, config: ConfigurationProvider): Animal
    +canPlace(animal: Animal, environment: Environment): boolean
}

class DefaultEnvironmentManager {
    -{static} BURROW_PROBABILITY: double = 0.01
    -{static} MOUNTAIN_ZONE_HEIGHT: int = 5
    -{static} SAVANNA_START_ROW: int = 20
    -{static} SAVANNA_END_ROW: int = 35
    -random: Random
    
    +DefaultEnvironmentManager()
    +populateEnvironments(field: Campo): void
    +getEnvironmentForPosition(row: int, col: int, fieldDepth: int, fieldWidth: int): Environment
}

class DefaultViewManager {
    -view: VisualizadorSimulacao
    
    +DefaultViewManager(depth: int, width: int)
    +setupColors(): void
    +showStatus(step: int, field: Campo): void
    +isViable(field: Campo): boolean
    +setAnimalColor(animalClass: Class<?>, color: Color): void
}

' ====================== CLASSES PRINCIPAIS ======================
class Simulador {
    -configProvider: ConfigurationProvider
    -animalFactory: AnimalFactory
    -environmentManager: EnvironmentManager
    -viewManager: ViewManager
    -{static} DEFAULT_WIDTH: int = 50
    -{static} DEFAULT_DEPTH: int = 50
    -animals: List<Animal>
    -newAnimals: List<Animal>
    -field: Campo
    -updatedField: Campo
    -step: int
    
    +Simulador()
    +Simulador(configProvider: ConfigurationProvider, animalFactory: AnimalFactory, environmentManager: EnvironmentManager)
    +Simulador(depth: int, width: int)
    -initializeSimulator(depth: int, width: int): void
    +simulateOneStep(): void
    +reset(): void
    -populate(field: Campo): void
    +getStep(): int
    +getAnimals(): List<Animal>
    +getConfigProvider(): ConfigurationProvider
}

class VisualizadorSimulacao {
    -{static} EMPTY_COLOR: Color = Color.WHITE
    -{static} UNKNOWN_COLOR: Color = Color.GRAY
    -{static} MOUNTAIN_COLOR: Color = Color.DARK_GRAY
    -{static} SAVANNA_COLOR: Color = Color.YELLOW
    -{static} PLAINS_COLOR: Color = Color.GREEN
    -{static} BURROW_COLOR: Color = Color.decode("#8B4513")
    -STEP_PREFIX: String = "Passo: "
    -POPULATION_PREFIX: String = "Populacao: "
    -stepLabel: JLabel
    -population: JLabel
    -fieldView: FieldView
    -colors: HashMap<Class<?>, Color>
    -stats: EstatisticasCampo
    -frame: JFrame
    
    +VisualizadorSimulacao(height: int, width: int)
    +setColor(animalClass: Class<?>, color: Color): void
    -getColor(animalClass: Class<?>): Color
    -getEnvironmentColor(env: Environment): Color
    +showStatus(step: int, field: Campo): void
    +isViable(field: Campo): boolean
    -createFrame(height: int, width: int): void
    +getFrame(): JFrame
}

class ControleSimulacaoInterativa {
    -simulator: Simulador
    -stopButton: JButton
    -pauseButton: JButton
    -runCompleteButton: JButton
    -statusLabel: JLabel
    -stepLabel: JLabel
    -fastTimer: Timer
    -isPaused: boolean
    -currentStep: int
    -maxSteps: int
    
    +ControleSimulacaoInterativa(simulator: Simulador)
    -setupUI(): void
    -stopSimulation(): void
    -togglePause(): void
    -runComplete(): void
    -updateStatus(): void
}

class Principal {
    +{static} main(args: String[]): void
}

' ====================== RELACIONAMENTOS ======================

' Herança
Animal --|> Simulable : implements ◁
Rabbit --|> Animal : extends △
Fox --|> Animal : extends △  
Lion --|> Animal : extends △
Human --|> Animal : extends △

' Implementações de Interfaces
Rabbit ..|> Prey : implements ◁
Fox ..|> Predator : implements ◁
Lion ..|> Predator : implements ◁
Human ..|> Predator : implements ◁

Mountain --|> Environment : extends △
Savanna --|> Environment : extends △
Plains --|> Environment : extends △
Burrow --|> Environment : extends △

ConfigurationManager ..|> ConfigurationProvider : implements ◁
ConcreteAnimalFactory ..|> AnimalFactory : implements ◁
DefaultEnvironmentManager ..|> EnvironmentManager : implements ◁
DefaultViewManager ..|> ViewManager : implements ◁

' Composição (owns-a) ◆
Simulador *-- Campo : owns ◆
Simulador *-- "1..*" Animal : creates ◆
Campo *-- Environment : contains ◆
VisualizadorSimulacao *-- EstatisticasCampo : owns ◆
DefaultViewManager *-- VisualizadorSimulacao : owns ◆
ControleSimulacaoInterativa *-- Simulador : owns ◆

' Agregação (has-a) ◇
Simulador o-- ConfigurationProvider : uses ◇
Simulador o-- AnimalFactory : uses ◇
Simulador o-- EnvironmentManager : uses ◇
Simulador o-- ViewManager : uses ◇
Animal o-- Location : has ◇
EstatisticasCampo o-- Counter : manages ◇

' Dependências (uses) →
Animal ..> Campo : uses →
Animal ..> Location : uses →
ConcreteAnimalFactory ..> ConfigurationProvider : uses →
ConcreteAnimalFactory ..> Environment : uses →
DefaultEnvironmentManager ..> Environment : creates →
VisualizadorSimulacao ..> Campo : displays →
Principal ..> Simulador : creates →
Principal ..> ControleSimulacaoInterativa : creates →

' ====================== CLASSES INTERNAS E AUXILIARES ======================
class "FieldView" {
    -gridHeight: int
    -gridWidth: int 
    -xScale: int
    -yScale: int
    -colors: Map<Class<?>, Color>
    
    +FieldView(height: int, width: int)
    +preparePaint(): void
    +drawMark(x: int, y: int, color: Color): void
    +paintComponent(g: Graphics): void
}

' ====================== MULTIPLICIDADES DETALHADAS ======================
Simulador *-- "1" Campo : owns ◆
Simulador *-- "0..*" Animal : manages ◆
Simulador o-- "1" ConfigurationProvider : uses ◇
Simulador o-- "1" AnimalFactory : uses ◇
Simulador o-- "1" EnvironmentManager : uses ◇  
Simulador o-- "0..1" ViewManager : uses ◇

Campo *-- "depth*width" Environment : contains ◆
Campo ..> "0..*" Animal : places →
Campo ..> "0..*" Location : uses →

Animal o-- "1" Location : has ◇
Animal ..> Campo : uses →

VisualizadorSimulacao *-- "1" EstatisticasCampo : owns ◆
VisualizadorSimulacao *-- "1" FieldView : owns ◆
VisualizadorSimulacao *-- "1" JFrame : owns ◆

DefaultViewManager *-- "1" VisualizadorSimulacao : owns ◆
ControleSimulacaoInterativa *-- "1" Simulador : owns ◆

EstatisticasCampo o-- "0..*" Counter : manages ◇

ConcreteAnimalFactory ..> "4" Animal : creates →
DefaultEnvironmentManager ..> "4" Environment : creates →

' ====================== NOTAS E PADRÕES DETALHADOS ======================
note right of Simulador 
**CLASSE CENTRAL - FAÇADE PATTERN**
- Coordena toda a simulação
- Dependency Injection completa
- Baixo acoplamento com interfaces
- Gerencia ciclo de vida dos animais
- Controla fluxo temporal da simulação
end note

note right of Animal 
**TEMPLATE METHOD PATTERN**
- breed() define algoritmo template
- Métodos abstratos implementados nas subclasses
- Hook methods para personalização
- Estado compartilhado (age, alive, location)
- Polimorfismo via act()
end note

note left of Campo 
**COMPOSITE + FLYWEIGHT PATTERNS**
- Contém grid bidimensional de Animals
- Matriz de Environments como contexto
- Gerencia posicionamento espacial
- Métodos de navegação adjacentes
- Iterator pattern para locations
end note

note bottom of ConfigurationProvider 
**STRATEGY + SINGLETON PATTERNS**
- Interface para diferentes fontes config
- Properties file como implementação default
- Permite extensão para DB, XML, etc
- Centraliza todas as configurações
end note

note top of AnimalFactory 
**ABSTRACT FACTORY PATTERN**
- Criação centralizada e desacoplada
- Probabilistic creation algorithm
- Environment validation integration
- Extensível para novos tipos de animais
end note

note left of Environment
**STRATEGY PATTERN**
- Diferentes regras de entrada por ambiente
- Polimorfismo para validação
- Fácil extensão para novos ambientes
- Separação de responsabilidades
end note

note right of ViewManager
**OBSERVER + ADAPTER PATTERNS**
- Observa mudanças no estado do Campo
- Adapta modelo para visualização
- Interface para diferentes tipos de view
- Desacopla lógica de apresentação
end note

note bottom of ControleSimulacaoInterativa
**COMMAND + STATE PATTERNS**
- Encapsula ações de controle
- Estados de simulação (paused, running, stopped)
- Timer-based execution control
- Event-driven user interaction
end note

note top of EstatisticasCampo
**OBSERVER + VISITOR PATTERNS**
- Observa população do campo
- Visita cada posição para contar
- Cache de contagens para performance
- Lazy evaluation de estatísticas
end note

' ====================== ESTEREÓTIPOS E RESPONSABILIDADES ======================
class Principal <<main>>
abstract class Animal <<entity>>
abstract class Environment <<value object>>
interface Simulable <<behavior>>
interface Predator <<behavior>>
interface Prey <<behavior>>
class ConfigurationManager <<service>>
class ConcreteAnimalFactory <<factory>>
class DefaultEnvironmentManager <<manager>>
class DefaultViewManager <<adapter>>
class Campo <<aggregate root>>
class Location <<value object>>
class Counter <<utility>>
class EstatisticasCampo <<service>>
class VisualizadorSimulacao <<view>>
class ControleSimulacaoInterativa <<controller>>
class Simulador <<facade>>

@enduml
```

## Análise Arquitetural Completa dos Padrões de Design

### 1. **INTERFACES E CONTRATOS (Interface Segregation Principle)**

#### **Simulable Interface**
- **Propósito**: Define contrato básico para entidades simuláveis
- **Métodos**: `act()`, `isAlive()`
- **Implementações**: Animal (abstract) → Fox, Rabbit, Lion, Human
- **Padrão**: Strategy Pattern para comportamentos polimórficos

#### **Predator Interface** 
- **Propósito**: Comportamentos específicos de predadores
- **Métodos**: `hunt()`, `getFoodLevel()`, `isHungry()`
- **Implementações**: Fox, Lion, Human
- **Padrão**: Role Interface separando responsabilidades

#### **Prey Interface**
- **Propósito**: Comportamentos específicos de presas
- **Métodos**: `escape()`, `detectsPredators()`
- **Implementações**: Rabbit (única presa pura)
- **Padrão**: Interface Segregation (separada de Predator)

#### **Service Interfaces**
- **ConfigurationProvider**: Strategy para configuração
- **AnimalFactory**: Abstract Factory para criação
- **EnvironmentManager**: Factory para ambientes
- **ViewManager**: Adapter para apresentação

### 2. **HIERARQUIAS DE HERANÇA E TEMPLATE METHOD**

#### **Animal Hierarchy (Template Method Pattern)**
```
Animal (abstract)
├── Rabbit implements Prey
├── Fox implements Predator  
├── Lion implements Predator
└── Human implements Predator
```
- **Template Method**: `breed()` define algoritmo geral
- **Hook Methods**: `createOffspring()`, `getMaxAge()`, etc.
- **Shared State**: age, alive, location, Random rand
- **Polymorphism**: `List<Animal>` permite tratamento uniforme

#### **Environment Hierarchy (Strategy Pattern)**
```
Environment (abstract)
├── Mountain
├── Savanna
├── Plains 
└── Burrow
```
- **Strategy Method**: `canEnter(Animal)` - regras específicas
- **Polymorphism**: Diferentes validações por ambiente
- **Factory**: DefaultEnvironmentManager cria instâncias

### 3. **RELACIONAMENTOS UML DETALHADOS**

#### **Composição (◆) - Strong "owns-a"**
- `Simulador *-- Campo`: Simulador POSSUI Campo (lifecycle dependency)
- `Campo *-- Environment[][]`: Campo POSSUI matriz de Environments
- `VisualizadorSimulacao *-- FieldView`: View POSSUI componente gráfico
- `VisualizadorSimulacao *-- EstatisticasCampo`: View POSSUI estatísticas
- `DefaultViewManager *-- VisualizadorSimulacao`: Manager POSSUI View

#### **Agregação (◇) - Weak "has-a"**
- `Simulador o-- ConfigurationProvider`: Simulador USA configuração
- `Simulador o-- AnimalFactory`: Simulador USA factory 
- `Simulador o-- EnvironmentManager`: Simulador USA manager
- `Animal o-- Location`: Animal TEM localização
- `EstatisticasCampo o-- Counter`: Stats GERENCIA contadores

#### **Dependência (→) - "uses" temporariamente**
- `Animal ..> Campo`: Animal usa Campo durante act()
- `ConcreteAnimalFactory ..> ConfigurationProvider`: Factory usa config
- `Principal ..> Simulador`: Main cria e usa Simulador

#### **Multiplicidades Específicas**
- `Campo[1] *-- Environment[depth×width]`: Matriz completa
- `Simulador[1] o-- Animal[0..*]`: Lista dinâmica de animais
- `EstatisticasCampo[1] o-- Counter[0..*]`: Map de contadores por classe

### 4. **PADRÕES DE DESIGN IMPLEMENTADOS (GoF + Outros)**

#### **Creational Patterns**
1. **Abstract Factory Pattern**
   - `AnimalFactory` + `ConcreteAnimalFactory`
   - Criação desacoplada de diferentes tipos de animais
   - Probabilistic creation baseado em configuração

2. **Factory Method Pattern**
   - `DefaultEnvironmentManager.getEnvironmentForPosition()`
   - Criação contextual baseada em posição geográfica

#### **Structural Patterns**
3. **Adapter Pattern**
   - `DefaultViewManager` adapta `Simulador` para `VisualizadorSimulacao`
   - Interface comum para diferentes tipos de visualização

4. **Composite Pattern**
   - `Campo` contém matriz de `Environment` e lista de `Animal`
   - Tratamento uniforme de elementos complexos

5. **Facade Pattern**
   - `Simulador` fornece interface simplificada para sistema complexo
   - Coordena ConfigurationProvider, AnimalFactory, EnvironmentManager

#### **Behavioral Patterns**
6. **Template Method Pattern**
   - `Animal.breed()` define algoritmo template
   - Subclasses implementam steps específicos via hook methods

7. **Strategy Pattern**
   - `Environment.canEnter()` - diferentes estratégias de validação
   - `ConfigurationProvider` - diferentes fontes de configuração

8. **Observer Pattern**
   - `ViewManager` observa estado do `Campo`
   - `EstatisticasCampo` observa população para estatísticas

9. **Command Pattern**
   - `ControleSimulacaoInterativa` encapsula comandos de controle
   - Actions: stop, pause, run, reset

10. **State Pattern**
    - `ControleSimulacaoInterativa` gerencia estados (paused, running, stopped)
    - Transições controladas via botões

#### **Architectural Patterns**
11. **Model-View-Controller (MVC)**
    - **Model**: Simulador, Campo, Animal, Environment
    - **View**: VisualizadorSimulacao, FieldView
    - **Controller**: ControleSimulacaoInterativa

12. **Dependency Injection Pattern**
    - `Simulador` recebe dependências via construtor
    - Inversão de controle para baixo acoplamento

### 5. **PRINCÍPIOS SOLID RIGOROSAMENTE APLICADOS**

#### **Single Responsibility Principle (SRP)**
- ✅ `Animal`: Gerencia estado e comportamento de animal individual
- ✅ `Campo`: Gerencia grid espacial e posicionamento
- ✅ `ConfigurationManager`: Gerencia apenas configurações
- ✅ `EstatisticasCampo`: Gerencia apenas estatísticas populacionais
- ✅ `VisualizadorSimulacao`: Gerencia apenas apresentação visual

#### **Open-Closed Principle (OCP)**
- ✅ Novas espécies: herdar de `Animal` + implementar `Predator`/`Prey`
- ✅ Novos ambientes: herdar de `Environment`
- ✅ Novas configurações: implementar `ConfigurationProvider`
- ✅ Novas visualizações: implementar `ViewManager`

#### **Liskov Substitution Principle (LSP)**
- ✅ Todas as subclasses de `Animal` substituem a classe base
- ✅ Todas as implementações de interfaces são intercambiáveis
- ✅ `List<Animal>` funciona com qualquer subclasse

#### **Interface Segregation Principle (ISP)**
- ✅ `Predator` e `Prey` separadas (nem todos animais são ambos)
- ✅ `ConfigurationProvider`, `AnimalFactory`, `ViewManager` específicas
- ✅ Clientes dependem apenas de interfaces que usam

#### **Dependency Inversion Principle (DIP)**
- ✅ `Simulador` depende de abstrações, não implementações concretas
- ✅ High-level modules (`Simulador`) não dependem de low-level (`Fox`, `Rabbit`)
- ✅ Dependency Injection inverte o controle

### 6. **MÉTRICAS DE QUALIDADE ARQUITETURAL**

#### **Baixo Acoplamento (Low Coupling)**
- `Simulador` conhece apenas interfaces, não classes concretas
- `Animal` não conhece implementações específicas de `Campo`
- `VisualizadorSimulacao` independente de lógica de negócio

#### **Alta Coesão (High Cohesion)**
- Classes com responsabilidade única e bem definida
- Métodos dentro de cada classe são altamente relacionados
- Baixa complexidade ciclomática

#### **Extensibilidade e Manutenibilidade**
- ✅ Fácil adicionar novos animais (herança + interface)
- ✅ Fácil mudar algoritmos (Strategy pattern)
- ✅ Fácil modificar visualização (Adapter pattern)
- ✅ Configuração externa via Properties file

### 7. **CONSIDERAÇÕES AVANÇADAS DE DESIGN**

#### **Thread Safety e Concorrência**
- Classes são thread-safe para leitura concorrente
- `Timer` em `ControleSimulacaoInterativa` gerencia concorrência temporal
- Estado mutável protegido por design

#### **Memory Management e Performance**
- `Object[][]` em `Campo` otimizado para acesso espacial
- `HashMap` para cores e contadores - O(1) lookup
- Lazy evaluation em `EstatisticasCampo`

#### **Testability e Mockability**
- Interfaces permitem easy mocking para unit tests
- Dependency injection facilita test doubles
- Estado observável via getters públicos

#### **Configuration Management**
- Properties file externo para deployment flexibility
- Default values para robustez
- Validation e error handling para configurações inválidas

### 8. **CONCLUSÃO ARQUITETURAL**

Este sistema demonstra uma **arquitetura orientada a objetos exemplar** que implementa:

- ✅ **29 classes** organizadas em hierarquias coesas
- ✅ **7 interfaces** bem segregadas e específicas  
- ✅ **12+ Design Patterns** clássicos e modernos
- ✅ **5 Princípios SOLID** rigorosamente aplicados
- ✅ **MVC Architecture** com separação clara de responsabilidades
- ✅ **Dependency Injection** para baixo acoplamento
- ✅ **Strategy, Factory, Template Method** para extensibilidade
- ✅ **Observer, Command, State** para interatividade

A arquitetura suporta **fácil manutenção**, **extensibilidade sem modificação**, **testabilidade completa** e **separação clara de responsabilidades**. É um **exemplo de referência** para desenvolvimento orientado a objetos em Java.