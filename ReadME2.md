# Análise das Melhorias
#### 1. Saber Custos dos Tempos de Pipeline
Implementação de métricas para avaliar e monitorar o tempo gasto em cada estágio do pipeline, permitindo ajustes para maior eficiência.

#### 2. Adição de Plugins no Jenkins
Jenkins Plugin Viewer:
Oferece uma interface para visualizar e gerenciar os plugins instalados, otimizando a manutenção do ambiente Jenkins.
Stage View Plugin:
Melhora a visualização dos estágios do pipeline, com informações detalhadas sobre tempos e status de execução.

#### 3. Análise Crítica
Fizemos uma análise detalhada do pipeline para discutir como reduzir tempos de execução. Isso incluiu:
Paralelização de testes unitários: Configuração para rodar testes independentes em paralelo, reduzindo o tempo total.
Mutation Testing: Avaliação da resiliência do código por meio da integração de mutation testing.

#### 4. Integração de Mutation Testing
Adição de ferramentas para mutation testing, permitindo verificar se os testes existentes detectam alterações deliberadas no código.

#### 5. Thresholds no SonarQube
Configuração de limites mínimos de qualidade para impedir que código de baixa qualidade seja integrado, promovendo melhorias contínuas.

#### 6. Pipeline em Execução
Pipeline configurado e funcionando corretamente, incluindo integração contínua, execução de testes, análise de qualidade de código e relatórios automatizados.

#### Itens Implementados:
[x] Saber custos dos tempos de pipeline
[x] Adicionar Jenkins Plugin Viewer
[x] Adicionar Stage View Plugin
[x] Análise crítica: discussão sobre tempos e paralelização de testes unitários, integração e mutation
[x] Integração de mutation testing na pipeline
[x] Configurar thresholds de análise no SonarQube
[x] Pipeline configurada e em execução

#### Itens Pendentes:
[ ] Acrescentar estágio de "packaging" para o Tomcat
[ ] Estabelecer como distinguir entre:
[ ] Jenkins para deployment de aplicações
[ ] Docker para execução de aplicações
[ ] Diferenciar a apresentação da cobertura de testes unitários em relação a outros tipos de testes
