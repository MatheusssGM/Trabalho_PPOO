# Simulador Predador-Presa - Projeto POO Melhorado

## Visão Geral

Este projeto implementa um simulador de ecossistema predador-presa que foi completamente reformulado para demonstrar boas práticas de design orientado a objetos. O simulador modela a interação entre diferentes espécies de animais (coelhos, raposas e lobos) em um ambiente controlado.

## Conceitos de POO Implementados

### 1. **Herança**
- **Classe Abstrata Animal**: Base comum para todos os animais
  - Define atributos: `age`, `alive`, `location`  
  - Implementa comportamentos comuns: `incrementAge()`, `breed()`, `canBreed()`
  - Métodos abstratos forçam implementação específica nas subclasses

- **Hierarquia de Classes**:
  ```
  Animal (abstract)
  ├── Fox (Raposa)
  ├── Rabbit (Coelho)  
  └── Wolf (Lobo - nova espécie)
  ```

### 2. **Polimorfismo**
- **Método `act()`**: Cada animal implementa seu comportamento único
- **Coleções Polimórficas**: `List<Animal>` armazena diferentes tipos
- **Binding Dinâmico**: `animal.act()` chama o método correto em tempo de execução

### 3. **Classes e Métodos Abstratos**
- **Animal** não pode ser instanciada diretamente
- **Métodos abstratos**: `act()`, `createOffspring()`, `getMaxAge()`, etc.
- **Template Method Pattern**: Estrutura comum com implementação específica

### 4. **Interfaces**
- **`Simulable`**: Contrato básico para elementos da simulação
- **`Predator`**: Comportamento específico para predadores
  - `hunt()`: Caça presas
  - `getFoodLevel()`: Nível de energia
  - `isHungry()`: Estado de fome
- **`Prey`**: Comportamento específico para presas
  - `escape()`: Fuga de predadores
  - `detectsPredators()`: Detecção de ameaças

### 5. **Generics e Coleções**
- **Type Safety**: `List<Animal>`, `Iterator<Location>`, `Map<Class<?>, Counter>`
- **Eliminação de Casting**: Código mais seguro e legível
- **Field melhorado**: `Animal[][]` em vez de `Object[][]`

### 6. **Interface Gráfica (GUI)**
- **SimulatorView**: Visualização gráfica em tempo real
- **Cores por espécie**: 
  - 🟠 Coelhos (Orange)
  - 🔵 Raposas (Blue) 
  - 🔴 Lobos (Red)
- **Atualização dinâmica**: Mostra evolução do ecossistema

## Estrutura do Projeto

```
src/
├── Animal.java          # Classe abstrata base
├── Fox.java            # Raposa (predador)
├── Rabbit.java         # Coelho (presa)
├── Wolf.java           # Lobo (predador superior) - NOVO
├── Simulable.java      # Interface principal
├── Predator.java       # Interface para predadores - NOVA
├── Prey.java           # Interface para presas - NOVA
├── Simulator.java      # Motor da simulação (melhorado)
├── Field.java          # Campo com generics
├── FieldStats.java     # Estatísticas com generics
├── Location.java       # Coordenadas
├── Counter.java        # Contador de população
├── SimulatorView.java  # Interface gráfica
└── Principal.java      # Ponto de entrada
```

## Principais Melhorias Implementadas

### 🔄 **Sistema de Herança Robusto**
- Eliminação de duplicação de código
- Comportamentos comuns centralizados na classe `Animal`
- Especialização através de override em subclasses

### 🎯 **Polimorfismo Efetivo**
- Simulador processa qualquer tipo de animal de forma uniforme
- Fácil adição de novas espécies sem modificar código existente
- Comportamentos específicos através de method dispatch

### 🔗 **Interfaces Bem Definidas**
- Separação clara entre predadores e presas
- Contratos específicos para diferentes comportamentos
- Múltipla herança através de interfaces

### 🛡️ **Type Safety com Generics**
- Eliminação completa de casting perigoso
- Detecção de erros em tempo de compilação
- Código mais legível e manutenível

### 🌟 **Funcionalidades Avançadas**
- **Lobos**: Predadores superiores que caçam raposas e coelhos
- **Sistema de Fuga**: Coelhos detectam e fogem de predadores
- **Hierarquia Alimentar**: Cadeia alimentar realística (Coelho → Raposa → Lobo)
- **Comportamento Inteligente**: Animais tomam decisões baseadas no ambiente

### 📊 **Estatísticas Melhoradas**
- Contadores tipados com generics
- Tracking de múltiplas espécies
- Interface mais limpa e extensível

## Como Executar

### Compilação:
```bash
cd src
javac *.java
```

### Execução:
```bash
java Principal
```

## Demonstração dos Conceitos

1. **Herança**: Código comum em `Animal`, especialização nas subclasses
2. **Polimorfismo**: `for(Animal animal : animals) { animal.act(); }`
3. **Interfaces**: Predadores implementam `hunt()`, presas implementam `escape()`
4. **Generics**: Coleções tipadas garantem segurança
5. **Abstração**: `Animal` define template, implementação nas subclasses
6. **Encapsulamento**: Atributos protegidos, acesso via métodos

## Benefícios da Refatoração

- ✅ **Extensibilidade**: Fácil adição de novas espécies
- ✅ **Manutenibilidade**: Código organizado e bem documentado  
- ✅ **Reutilização**: Herança elimina duplicação
- ✅ **Flexibilidade**: Interfaces permitem múltiplos comportamentos
- ✅ **Segurança**: Generics previnem erros de tipo
- ✅ **Performance**: Binding eficiente através de polimorfismo

## Como Adicionar uma Nova Espécie

O design orientado a objetos facilita a extensão do sistema. Para adicionar uma nova espécie de animal, siga estes passos:

### Exemplo: Adicionando um Urso

#### 1. **Criar a Classe**
```java
public class Bear extends Animal implements Predator {
    // Constantes específicas do urso
    private static final int BREEDING_AGE = 20;
    private static final int MAX_AGE = 300;
    private static final double BREEDING_PROBABILITY = 0.03;
    private static final int MAX_LITTER_SIZE = 2;
    private static final int PREY_FOOD_VALUE = 10;
    
    private int foodLevel;
    
    public Bear(boolean randomAge) {
        super(randomAge);
        foodLevel = randomAge ? rand.nextInt(PREY_FOOD_VALUE) : PREY_FOOD_VALUE;
    }
    
    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        incrementAge();
        decreaseFoodLevel();
        if(isAlive()) {
            // Implementar comportamento específico do urso
            reproduce(updatedField, newAnimals);
            Location newLocation = hunt(currentField, location);
            if(newLocation == null) {
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                setDead();
            }
        }
    }
    
    // Implementar métodos abstratos obrigatórios
    @Override
    protected Animal createOffspring(boolean randomAge) {
        return new Bear(randomAge);
    }
    
    @Override
    protected int getMaxAge() { return MAX_AGE; }
    
    @Override
    protected int getBreedingAge() { return BREEDING_AGE; }
    
    @Override
    protected double getBreedingProbability() { return BREEDING_PROBABILITY; }
    
    @Override
    protected int getMaxLitterSize() { return MAX_LITTER_SIZE; }
    
    // Implementar interface Predator
    @Override
    public Location hunt(Field field, Location location) {
        // Implementar lógica de caça específica do urso
        // Pode caçar múltiplas presas
    }
    
    @Override
    public int getFoodLevel() { return foodLevel; }
    
    @Override
    public boolean isHungry() { return foodLevel < (PREY_FOOD_VALUE / 2); }
}
```

#### 2. **Atualizar o Simulador**
```java
// Em Simulator.java, adicionar:
private static final double BEAR_CREATION_PROBABILITY = 0.001;

// No método populate(), adicionar:
else if(probability <= BEAR_CREATION_PROBABILITY + /* outras probabilidades */) {
    Bear bear = new Bear(true);
    animals.add(bear);
    bear.setLocation(row, col);
    field.place(bear, row, col);
}

// No construtor, adicionar cor:
view.setColor(Bear.class, Color.DARK_GRAY);
```

#### 3. **Passos Obrigatórios**

✅ **Herdar de Animal**: Toda nova espécie deve estender `Animal`

✅ **Implementar métodos abstratos**:
- `act()`: Comportamento principal na simulação
- `createOffspring()`: Criação de filhotes
- `getMaxAge()`, `getBreedingAge()`, etc.: Parâmetros da espécie

✅ **Escolher interface(s)**:
- `Predator`: Se caça outros animais
- `Prey`: Se pode ser caçada
- Ambas: Se é predador e presa

✅ **Definir constantes**: Parâmetros específicos da nova espécie

✅ **Atualizar Simulator**: 
- Probabilidade de criação
- Cor na interface gráfica
- Lógica de população

#### 4. **Características Personalizáveis**

- **Idade máxima e de reprodução**
- **Probabilidade de nascimento**
- **Tamanho da ninhada**
- **Comportamento de caça/fuga**
- **Preferências alimentares**
- **Velocidade de movimento**
- **Resistência a fome**

#### 5. **Exemplo Completo: Coiote**
```java
public class Coyote extends Animal implements Predator, Prey {
    // Constantes...
    
    @Override
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        // Comportamento híbrido: caça coelhos, foge de lobos
        if(detectsPredators(currentField, location)) {
            // Comportamento de presa
            Location escapeLocation = escape(currentField, location);
            if(escapeLocation != null) {
                setLocation(escapeLocation);
                updatedField.place(this, escapeLocation);
                return;
            }
        }
        // Comportamento de predador
        Location huntLocation = hunt(currentField, location);
        // ... resto da implementação
    }
    
    // Implementar tanto Predator quanto Prey
    @Override
    public Location hunt(Field field, Location location) { /* caça coelhos */ }
    
    @Override
    public Location escape(Field field, Location location) { /* foge de lobos */ }
}
```

### Vantagens do Design

- 🔧 **Zero modificações** no código existente
- 🎯 **Polimorfismo automático** - funciona imediatamente com o simulador
- 🔄 **Reutilização total** dos comportamentos base da classe `Animal`  
- 🧩 **Interfaces flexíveis** para diferentes tipos de comportamento
- 📊 **Estatísticas automáticas** através do `FieldStats`
- 🎨 **Visualização imediata** na interface gráfica

Este projeto exemplifica como um design orientado a objetos bem estruturado resulta em código mais robusto, extensível e fácil de manter, utilizando todos os conceitos fundamentais da POO de forma prática e eficiente.
