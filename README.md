# Documentação do Projeto: Rotas Escolares

## 1. Visão Geral

O "Rotas Escolares" é um aplicativo Android desenvolvido para otimizar e gerenciar a logística de transporte escolar. Ele centraliza o cadastro e a gestão de alunos, responsáveis, escolas e funcionários, além de oferecer uma funcionalidade de controle de viagem em tempo real para monitorar o embarque e desembarque dos alunos.

O projeto foi construído seguindo as práticas modernas de desenvolvimento Android, com uma arquitetura robusta e escalável.

---

## 2. Funcionalidades Implementadas

- **Gerenciamento Completo (CRUD):**
  - **Alunos:** Cadastro, edição, visualização e exclusão de alunos.
  - **Responsáveis:** Gerenciamento dos dados dos responsáveis pelos alunos.
  - **Escolas:** Gerenciamento das escolas atendidas.
  - **Funcionários:** Cadastro e controle de motoristas e outros funcionários.

- **Integração com API Externa (ViaCEP):**
  - No cadastro de alunos, ao digitar um CEP válido de 8 dígitos, o aplicativo consulta a API do ViaCEP e preenche automaticamente os campos de logradouro, bairro, cidade e UF, agilizando o processo e reduzindo erros de digitação.

- **Controle de Viagem:**
  - Uma tela dedicada permite que o motorista visualize a lista de alunos de um determinado turno.
  - É possível atualizar o status de cada aluno durante a viagem (Ex: Aguardando, A Bordo, Desembarcou), fornecendo um controle preciso da operação.

- **Interface Moderna e Intuitiva:**
  - O aplicativo utiliza componentes do Material Design e um Navigation Drawer para facilitar a navegação entre as diferentes seções.

---

## 3. Tecnologias e Arquitetura

O projeto foi estruturado com base na arquitetura **MVVM (Model-View-ViewModel)**, que promove a separação de responsabilidades e facilita a manutenção e testabilidade do código.

- **Linguagem:** **Kotlin**.
- **Arquitetura:** MVVM.
- **Componentes de Arquitetura do Android:**
  - **ViewModel:** Armazena e gerencia os dados relacionados à UI, sobrevivendo a mudanças de configuração (como rotação de tela).
  - **LiveData:** Funciona como um observador que notifica a UI sobre alterações nos dados (por exemplo, quando uma lista de alunos é carregada do banco de dados).
  - **Room:** Biblioteca de persistência de dados que oferece uma abstração sobre o SQLite para permitir um acesso mais robusto ao banco de dados, com verificação de consultas em tempo de compilação.
  - **Navigation Component:** Gerencia a navegação entre as telas (Fragments) de forma centralizada e visual.
- **UI (Interface do Usuário):**
  - **View Binding:** Substitui o `findViewById` para fornecer uma maneira mais segura e concisa de interagir com as views.
  - **Material Design Components:** Utilizado para criar uma interface moderna e consistente com as diretrizes do Android.
  - **RecyclerView:** Usado para exibir listas de dados (alunos, escolas, etc.) de forma eficiente.
- **Rede:**
  - **Retrofit:** Cliente HTTP type-safe para Android e Java, usado para realizar as chamadas à API do ViaCEP.
  - **GSON:** Biblioteca para converter objetos Java (e Kotlin) em sua representação JSON e vice-versa.
- **Coroutines:** Utilizadas para gerenciar operações assíncronas, como chamadas de rede e acesso ao banco de dados, de forma a não bloquear a thread principal e manter a responsividade do app.

---

## 4. Estrutura do Projeto

O código-fonte está organizado em pacotes que separam as responsabilidades de forma clara:

```
app/src/main/java/com/example/rotasescolares/
│
├── data/                # Contém as classes do banco de dados (Room)
│   ├── AppDatabase.kt     # A classe principal do banco de dados
│   ├── Converters.kt      # Conversores de tipo para o Room
│   ├── DAOs (StudentDao, SchoolDao, etc.) # Interfaces com as consultas SQL
│   └── Entidades (Student, School, etc.)   # Classes que representam as tabelas
│
├── network/             # Classes relacionadas à comunicação de rede
│   ├── ApiClient.kt       # Objeto singleton que configura e provê o Retrofit
│   └── ViaCepService.kt   # Interface do Retrofit para a API ViaCEP
│
├── ui/                  # Pacote principal da camada de apresentação (View)
│   ├── studentmanagement/ # Tela de listagem de alunos
│   │   ├── StudentAdapter.kt
│   │   └── StudentManagementFragment.kt
│   │
│   ├── studentregistration/ # Tela de cadastro de aluno
│   │   ├── StudentRegistrationFragment.kt
│   │   └── StudentRegistrationViewModel.kt
│   │
│   └── ... (outros pacotes de UI como schoolmanagement, tripcontrol, etc.)
│
├── MainActivity.kt      # Ponto de entrada principal do app
└── ViaCepResponse.kt    # Data class para mapear a resposta da API ViaCEP
```

- **`res/layout/`**: Contém os arquivos XML que definem o layout de cada tela (Fragments e Activities) e dos itens das listas (RecyclerView).
- **`res/menu/`**: Define os menus do aplicativo, como o menu de opções (editar/excluir) nos itens das listas.
- **`res/values/`**: Contém recursos como strings, cores e estilos.
- **`build.gradle.kts`**: Arquivo de configuração do Gradle, onde as dependências do projeto são declaradas.

---

## 5. Descrição das Telas

- **Telas de Gerenciamento (Alunos, Escolas, etc.):**
  - Exibem uma lista de itens cadastrados usando um `RecyclerView`.
  - Cada item da lista possui um menu de opções (representado por três pontos verticais) que permite **editar** ou **excluir** o registro.
  - Um botão de adição (FAB) permite navegar para a tela de cadastro correspondente.

- **Telas de Cadastro (Alunos, Escolas, etc.):**
  - Apresentam formulários com campos de texto e menus suspensos (`AutoCompleteTextView`) para inserir ou editar dados.
  - A tela de cadastro de alunos se destaca pela integração com o ViaCEP, que preenche os campos de endereço automaticamente.
  - A validação garante que campos obrigatórios não sejam deixados em branco.

- **Tela de Controle de Viagem:**
  - Permite ao motorista selecionar um turno (manhã, tarde, noite).
  - Exibe os alunos do turno selecionado em cards.
  - O status de cada aluno é indicado por uma cor e um texto (Aguardando, A Bordo).
  - Clicar em um aluno altera seu status, simulando o processo de embarque e desembarque.

---

## 6. Como Executar o Projeto

1.  Clone este repositório em sua máquina local.
2.  Abra o projeto no Android Studio.
3.  O Android Studio detectará as configurações do Gradle e solicitará a sincronização. Clique em **"Sync Now"**.
4.  Após a sincronização e o build, execute o aplicativo em um emulador ou dispositivo físico.

