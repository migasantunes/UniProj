# Análise e Transformação de Dados - Projeto 2025

Este repositório contém o código e os ficheiros desenvolvidos para o projeto da disciplina de Análise e Transformação de Dados (ATD). O objetivo principal do projeto é a identificação automatizada de dígitos através de características extraídas de sinais de áudio. 

O reconhecimento passa pela análise dos sinais de voz nos domínios do tempo e da frequência para discriminar palavras correspondentes aos dígitos em inglês (0 a 9).

## Sobre os Dados (AudioMNIST)
O projeto recorre a uma base de dados de voz padronizada com as seguintes características:
* **Fonte:** Dados baseados no repositório *AudioMNIST*.
* **Participantes:** Vozes recolhidas de 60 participantes diferentes.
* **Amostras:** Cada um dos participantes repetiu 50 vezes cada dígito entre 0 e 9.
* **Formato:** Os sinais áudio encontram-se no formato `.wav`.
* **Qualidade:** Foram adquiridos a uma taxa de amostragem de 48000 Hz, em modo mono-canal.

## Estrutura do Projeto e Metas
O desenvolvimento foi faseado em quatro metas principais. O código atual cobre grande parte do processamento, extração de características e classificação base inicial.

### Meta 1: Domínio do Tempo e Pré-processamento
* Criação de uma estrutura de dados sistemática (`table`) para indexar os diretórios, participantes, dígitos e o sinal bruto importado.
* **Pré-processamento:** Implementação de um alinhamento temporal. Remoção do silêncio inicial baseado no limiar de energia para garantir que todas as amostras começam ao mesmo tempo. A amplitude foi normalizada para evitar discrepâncias causadas pela distância ao microfone na gravação. Adição/corte de silêncio para uniformizar a duração total.
* **Extração de Características Temporais:** Cálculo de métricas globais e por janelas temporais, como energia, amplitudes máximas/mínimas e desvio padrão.
* **Visualização:** Representação gráfica das características extraídas através de *boxplots* para selecionar os atributos mais discriminativos.

### Meta 2: Domínio da Frequência
* Cálculo da série complexa de Fourier usando FFT para transitar a análise para o domínio espectral.
* Extração do espectro de amplitude mediano (normalizado pelo número de amostras) para as frequências positivas e análise de quartis.
* **Extração de Características Espectrais:** Determinação de picos (posição e amplitude), média espectral, *spectral edge frequency* a 85% e entropia. Visualização para apurar as melhores *features*.

### Meta 3: Classificação e Enjanelamento
* **Classificador:** Implementação de um modelo de Árvore de Decisão (`fitctree` no MATLAB) alimentado pelas melhores características combinadas (tempo + frequência) para prever automaticamente o dígito falado.
* Avaliação da performance através da exatidão (cálculo de acertos face ao total de dígitos) e de uma Matriz de Confusão.
* **Análise de Janelas:** Teste e comparação do efeito prático de diferentes janelas de processamento de sinal (como *Hamming*, *Hann* e *Blackman*).

### Meta 4: Tempo-Frequência (A Implementar)
* Uso da *Short-Time Fourier Transform* (STFT) com diferentes parametrizações para captar a evolução do espectro no tempo.
* Extração de características combinadas tempo-frequência e aplicação da Transformada Wavelet Discreta (DWT) para análise de energia por coeficientes de detalhe e aproximação.

## Tecnologias e Ferramentas
* **Linguagem:** MATLAB.

## Como Executar
1. Fazer o download do *dataset* e descompactar a pasta `data` com as subdiretorias dos participantes (01 a 60).
2. Ajustar a variável `caminhoBase` no código (ou no *Live Script*) para apontar para o diretório local onde os dados se encontram.
3. Executar as secções de código iterativamente. O progresso entre sessões é garantido pela leitura e escrita iterativa do ficheiro `dados_processados.mat`.