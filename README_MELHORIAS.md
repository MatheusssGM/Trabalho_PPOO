# Simulador Predador-Presa Melhorado

## Visão Geral
Este projeto implementa um simulador predador-presa melhorado usando conceitos avançados de Programação Orientada a Objetos (POO). O simulador foi redesenhado para demonstrar boas práticas de design de classes e utilização adequada de conceitos como herança, polimorfismo, métodos e classes abstratas, interfaces, coleções e interface gráfica.

## Conceitos de POO Implementados

### 1. Herança
- **Classe Abstrata Animal**: Classe base que define comportamentos comuns para todos os animais
  - Atributos: age, alive, location
  - Métodos abstratos: act(), createOffspring(), getMaxAge(), etc.
  - Métodos concretos: incrementAge(), breed(), canBreed()

- **Classes Filhas**: Fox, Rabbit e Wolf herdam de Animal
  - Cada classe implementa seus próprios valores para idade máxima, probabilidade de reprodução, etc.
  - Demonstra reutilização de código e especialização

### 2. Polimorfismo
- **Método act()**: Cada animal implementa seu próprio comportamento na simulação
- **Lista polimórfica**: `List<Animal>` contém diferentes tipos de animais
- **Comportamento dinâmico**: O simulador chama `animal.act()` sem saber o tipo específico

### 3. Classes e Métodos Abstratos
- **Animal**: Classe abstrata que não pode ser instanciada diretamente
- **Métodos abstratos**: Forçam as subclasses a implementar comportamentos específicos
- **Template Method Pattern**: Método `breed()` usa template definido na classe pai

### 4. Interfaces
- **Interface Simulable**: Define contrato para elementos que participam da simulação
- **Interface Predator**: Define comportamento específico para predadores
  - Métodos: hunt(), getFoodLevel(), isHungry()
- **Interface Prey**: Define comportamento específico para presas
  - Métodos: escape(), detectsPredators()

### 5. Uso de Generics (Coleções)
- **Field**: Usa `Animal[][]` em vez de `Object[][]`
- **Listas tipadas**: `List<Animal>`, `List<Location>`, `Map<Class<?>, Counter>`
- **Iteradores tipados**: `Iterator<Location>`
- **Type Safety**: Eliminação de casting desnecessário

### 6. Encapsulamento
- **Modificadores de acesso**: protected, private, public usados apropriadamente
- **Getters/Setters**: Acesso controlado aos atributos
- **Imutabilidade**: Location é imutável

### 7. Interface Gráfica (GUI)
- **SimulatorView**: Mantém a interface gráfica original
- **Cores diferentes**: Fox (azul), Rabbit (laranja), Wolf (vermelho)
- **Visualização em tempo real**: Atualização dinâmica do campo

## Estrutura de Classes

```
Animal (abstract)
├── Fox (implements Predator)
├── Rabbit (implements Prey)
└── Wolf (implements Predator)

Interfaces:
├── Simulable
├── Predator
└── Prey

Utilitárias:
├── Field (with generics)
├── Location
├── FieldStats (with generics)
├── Counter
├── Simulator
└── SimulatorView
```

## Melhorias Implementadas

### 1. **Extensibilidade**
- Fácil adição de novos tipos de animais
- Sistema flexível de predadores e presas
- Configuração através de constantes

### 2. **Manutenibilidade**
- Código bem organizado e documentado
- Responsabilidades bem definidas
- Eliminação de duplicação de código

### 3. **Type Safety**
- Uso consistente de generics
- Eliminação de casting perigoso
- Compilação type-safe

### 4. **Design Patterns**
- Template Method (na classe Animal)
- Strategy Pattern (através das interfaces)
- Observer Pattern (na interface gráfica)

### 5. **Funcionalidades Novas**
- Lobos como predadores superiores
- Sistema de detecção de predadores para presas
- Comportamento de fuga para coelhos
- Hierarquia alimentar mais complexa

## Como Executar

1. Compile todos os arquivos Java:
```bash
javac *.java
```

2. Execute o programa principal:
```bash
java Principal
```

## Demonstração dos Conceitos

- **Herança**: Todas as classes de animais herdam comportamentos básicos de Animal
- **Polimorfismo**: `animal.act()` chama o método apropriado para cada tipo
- **Interfaces**: Predators implementam hunt(), Prey implementam escape()
- **Generics**: Coleções tipadas garantem type safety
- **Abstração**: Animal define template para todos os animais
- **Encapsulamento**: Atributos protegidos com acesso controlado

Este projeto demonstra como um design orientado a objetos bem estruturado pode tornar o código mais flexível, extensível e fácil de manter, enquanto utiliza adequadamente os principais conceitos da programação orientada a objetos.