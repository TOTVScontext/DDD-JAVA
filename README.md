# DDD - Java | Plataforma Context

Este repositório contém a implementação da arquitetura de software para a Plataforma **Context**, desenvolvida para o Desafio Técnico TOTVS em parceria com a FIAP.

## 🎯 Sobre o Projeto
A solução foi estruturada sob os princípios de **Domain-Driven Design (DDD)**. O objetivo da plataforma é atuar no processamento e análise de transcrições de reuniões, transformando dados brutos em inteligência de negócios acionável (como riscos de churn e oportunidades de upsell).

## 📐 Modelagem do Sistema (UML)
Abaixo está a representação visual da nossa arquitetura, detalhando as entidades do domínio, suas relações e a aplicação de herança/polimorfismo.

<img width="4416" height="5112" alt="DDD_Challenge drawio" src="https://github.com/user-attachments/assets/4f79b11f-a222-4eee-8466-a406216a3317" />
 


## 🚀 Arquitetura e Padrões
O código-fonte reflete fielmente a modelagem projetada e aplica os conceitos fundamentais de Orientação a Objetos:
- **Encapsulamento:** Proteção de dados e uso de métodos de acesso.
- **Herança:** Estrutura hierárquica entre a classe pai `Insight` e suas especializações.
- **Polimorfismo:** Sobrescrita de métodos (`@Override`) para comportamentos específicos.

## 📁 Estrutura do Projeto
- `src/`: Código-fonte Java organizado por pacotes.
- `docs/`: Documentação técnica e diagrama UML em alta resolução.

## 🛠️ Como Executar
1. Clone o repositório.
2. Importe o projeto em sua IDE (Eclipse/IntelliJ).
3. Execute a classe `Main.java` no pacote principal.
4. Interaja via console/JOptionPane para simular o processamento de conversas.

---

## 👥 Integrantes da Equipe
| Nome | RM |
| :--- | :--- |
| Leonardo da Silva Pinto | `564929` |
| Samuel Enzo Domingues Monteiro | `564391` |
| Guilherme de Araujo Moreira | `561848` |

---
*Projeto desenvolvido para o NEXT FIAP em parceria com a TOTVS.*
