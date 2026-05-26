# Unibook Android

O **Unibook** é um ecossistema digital voltado para os alunos da **Unifor**, desenvolvido para proporcionar um melhor acesso à biblioteca da universidade, facilitando a consulta de livros, empréstimos e oferecendo suporte inteligente via IA.

## 🚀 Funcionalidades do Projeto

### 👨‍🎓 Para Alunos (Usuários)
- **🤖 Chatbot Inteligente:** Assistente virtual integrado (**Gemini 1.5-Flash**) para recomendação de livros, autores e dúvidas sobre o acervo. Possui persistência de mensagens ao trocar o tema do aplicativo.
- **📚 Consulta de Acervo:** Visualização do catálogo completo em lista ou grade, com busca avançada por título, autor ou gênero.
- **🔍 Detalhes da Obra:** Acesso a informações completas, sinopses e disponibilidade dos livros.
- **📖 Histórico de Leitura:** Acompanhamento de livros lidos e situação atual de empréstimos.
- **👤 Gestão de Perfil:** Personalização de dados cadastrais e busca por outros membros da comunidade acadêmica da Unifor.
- **💰 Controle Financeiro:** Verificação de multas por atraso e geração de QR Code para pagamentos.

### 🔐 Para Administradores
- **🛠 Gestão de Acervo (CRUD):** Ferramentas completas para cadastrar, editar e remover livros do sistema.
- **📋 Controle de Empréstimos:** Gerenciamento centralizado de solicitações e devoluções de obras.
- **👥 Gestão de Usuários:** Monitoramento de perfis e controle de pendências dos alunos.
- **⚠️ Alertas de Atraso:** Sistema para identificar e gerenciar multas e devoluções pendentes.

## 🛠 Tecnologias Utilizadas

- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **UI:** XML (Material Design) e [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Backend:** [Firebase](https://firebase.google.com/) (Firestore, Storage, Authentication)
- **IA Generativa:** [Google AI SDK (Gemini)](https://ai.google.dev/)
- **Carregamento de Imagens:** [Glide](https://github.com/bumptech/glide)
- **Persistência Local:** Room Database e SharedPreferences.
- **Injeção/Configuração:** Secrets Gradle Plugin para proteção de chaves de API.

## 📦 Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/franm/Unibook_Android.git
   ```
2. Abra o projeto no **Android Studio (Ladybug ou superior)**.
3. Configure o arquivo `local.properties` com sua `GEMINI_API_KEY`:
   ```properties
   GEMINI_API_KEY=SUA_CHAVE_AQUI
   ```
4. Certifique-se de ter o arquivo `google-services.json` na pasta `app/`.
5. Sincronize o Gradle e execute no emulador ou dispositivo físico (API 28+).

## 📄 Licença
Este projeto é para fins acadêmicos. Consulte os autores para mais detalhes sobre o uso do código.
