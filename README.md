⚡ Loja de Componentes Eletrônicos (Android)

Um aplicativo mobile moderno para gerenciamento e navegação de produtos em uma loja de componentes eletrônicos, contando com CRUD completo integrado ao Firebase (Firestore & Authentication) e interface desenvolvida em Jetpack Compose.

📌 Sobre o Projeto

Esse é um aplicativo Android focado em atender entusiastas, estudantes e profissionais da área de eletrônica. A plataforma permite listar, visualizar e gerenciar o estoque/catálogo de componentes eletrônicos (Create, Read, Update, Delete) em tempo real através do Firebase.

🚀 Funcionalidades

Autenticação de Usuários: Login e cadastro seguro utilizando o Firebase Authentication.

Catálogo de Produtos: Visualização fluida de componentes eletrônicos (como Arduino, Raspberry Pi, resistores, capacitores e sensores).

CRUD Completo:

Criar: Adição de novos produtos ao catálogo com nome, preço, categoria, estoque e imagem/URL.

Ler: Listagem e detalhamento dos componentes armazenados no Cloud Firestore.

Atualizar: Edição rápida das informações dos produtos em tempo real.

Deletar: Remoção de itens do banco de dados.

Interface Moderna: UI declarativa, reativa e adaptável construída com Jetpack Compose e Material Design 3.

🛠️ Tecnologias Utilizadas

Linguagem: Kotlin

UI Framework: Jetpack Compose (Material Design 3)

Arquitetura: MVVM (Model-View-ViewModel)

Gerenciamento de Estado: StateFlow / LiveData & ViewModel

Backend & Banco de Dados (Firebase):

Cloud Firestore: Banco de dados NoSQL em tempo real.

Firebase Authentication: Gestão e autenticação de usuários.

Carregamento de Imagens: Coil

IDE Recomendada: Android Studio

💻 Como Rodar o Projeto

Pré-requisitos

Android Studio (versão Ladybug / Jellyfish ou superior recomendada).

JDK 17 ou superior configurado.

Emulador Android (API 24+) ou um dispositivo físico com depuração USB ativada.

1. Clonar o Repositório

git clone https://github.com/joao-the-carvalho/AppFirebase.git


2. Configurar o Firebase no Android Studio

Crie um projeto no Firebase Console.

Ative os serviços de Authentication (E-mail e Senha) e Cloud Firestore.

No Firebase Console, adicione um aplicativo Android ao seu projeto com o mesmo Package Name definido no aplicativo (ex: com.example.appfirebase).

Faça o download do arquivo google-services.json.

Cole o arquivo google-services.json no diretório do projeto dentro da pasta /app:

AppFirebase/
└── app/
    └── google-services.json


3. Compilar e Executar

Abra o Android Studio.

Selecione Open e navegue até a pasta onde clonou o projeto AppFirebase.

Aguarde a sincronização do Gradle finalizar.

Selecione o seu emulador ou dispositivo físico e clique em Run (ou pressione Shift + F10).

📁 Estrutura do Projeto

app/src/main/java/com/.../
├── data/           # Modelos de dados e repositórios (Firestore/Auth)
│   ├── models/ # Modelos do App
│   ├── repository/ # Repositórios dos Modelos
├── ui/
│   ├── components/ # Componentes reutilizáveis do Jetpack Compose
│   ├── screens/    # Telas (Home, Login, Cadastro, Detalhes, Form CRUD)
│   └── theme/      # Configurações de Cores, Tipografia e Temas (Material3)
├── viewmodel/      # ViewModels para gerenciamento de estado da UI
└── MainActivity.kt # Ponto de entrada do aplicativo Android


✒️ Autor

Desenvolvido por João Victor de Paiva Carvalho.
