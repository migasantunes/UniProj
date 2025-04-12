#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "trabalho.h"

void menu(){
    printf("\n-------------------MENU-------------------\n");
    printf("1- Introduzir novo Doente\n");
    printf("2- Eliminar Doente\n");
    printf("3- Listar todos os Doentes\n");
    printf("4- Listar Doentes acima de uma tensão\n");
    printf("5- Apresentar informações de um Doente\n");
    printf("6- Novo registo de um Doente\n");
    printf("0- Sair\n");
    printf("------------------------------------------\n");
    printf("Opção--> ");
}

lista cria(){
    lista aux;
    Doente d;
    aux = (lista) malloc (sizeof(node));
    if (aux != NULL){
        aux->registo_doente = NULL;
        aux->doente = d;
        aux->prox = NULL;
    }
    return aux;
}

lista_reg cria_registo(){
    lista_reg aux;
    Registo r;
    aux = (lista_reg) malloc (sizeof(node_reg));
    if (aux != NULL){
        aux->registo = r;
        aux->prox = NULL;
    }
}

lista_ten cria_tensoes(){
    lista_ten aux;
    char nome[50];
    int tensao;
    aux = (lista_ten) malloc (sizeof(node_tensao));
    if (aux != NULL){
        aux->doente.tensao = tensao;
        strcpy(aux->doente.nome,nome);
        aux->prox = NULL;
    }
    return aux;
}

int vazia(lista l){
    if(l->prox == NULL) return 1;
    else return 0;
}

int vazia_registo(lista_reg l){
    if(l->prox == NULL) return 1;
    else return 0;
}

int vazia_tensao(lista_ten l){
    if(l->prox == NULL) return 1;
    else return 0;
}

lista destroi(lista l){
    lista newnode;
    while (!vazia(l)){
        newnode = l;
        l = l->prox;
        if (newnode->registo_doente != NULL)destroi_reg(newnode->registo_doente);
        if(newnode->prox != NULL)free(newnode);
    }
    if (l->registo_doente != NULL)destroi_reg(l->registo_doente);
    if(l!=NULL)free(l);
    return NULL;
}

lista_reg destroi_reg(lista_reg l){
    lista_reg newnode;
    while (!vazia_registo(l)){
        newnode = l;
        l = l->prox;
        if (newnode->prox != NULL)free(newnode);
    }
    if(l!=NULL)free(l);
    return NULL;
}

lista_ten destroi_ten(lista_ten l){
    lista_ten newnode;
    while (!vazia_tensao(l)){
        newnode = l;
        l = l->prox;
        free(newnode);
    }
    free(l);
    return NULL;
}

void minus(char n1[],char n2[],int tam1,int tam2){
    for (int i = 0; i < tam1; i++){
        if (n1[i] != ' ' && n1[i] != '\0' && 'A' <= n1[i] && n1[i] <= 'Z') n1[i] = (n1[i] - 'A')+'a';
    }
    for (int i = 0; i < tam2; i++){
        if (n2[i] != ' ' && n2[i] != '\0' && 'A' <= n2[i] && n2[i] <= 'Z') n2[i] = (n2[i] - 'A')+'a';
    }
}

void format_nome(char n[]){
    char ant = ' ';
    for(int i = 0; i < strlen(n); i++){
        if (ant == ' ' && n[i] != '\0' && n[i] != ' ' && 'a' <= n[i] && n[i] <= 'z') n[i] = (n[i] - 'a')+'A';
        else if (ant != ' ' && n[i] != '\0' && n[i] != ' ' && 'A' <= n[i] && n[i] <= 'Z') n[i] = (n[i] - 'A')+'a';
        ant = n[i];
    }
}

int verifica_alfabeto(char n1[], char n2[]){
    int tam1 = strlen(n1), tam2 = strlen(n2);
    minus(n1,n2,tam1,tam2);
    if (strcmp(n1,n2)==0) return 0;
    else if(strcmp(n1,n2)<0) return -1;
    else if(strcmp(n1,n2)>0) return 1;
}

void procura(lista l, char nome[], lista *ant, lista *act){
    *ant = l;
    *act = l->prox;
    while ((*act)!=NULL && verifica_alfabeto((*act)->doente.nome,nome)==-1 && !vazia(l)){
        *ant = *act;
        *act = (*act)->prox;
    }
    if ((*act) != NULL && verifica_alfabeto((*act)->doente.nome,nome)!=0) *act = NULL;
}

void procura_id(lista l, char id[], lista *ant, lista *act){
    *ant = l;
    *act = l->prox;
    while ((*act)!=NULL && atoi((*act)->doente.id) != atoi(id) && !vazia(l)){
        *ant = *act;
        *act = (*act)->prox;
    }
    if ((*act) != NULL && atoi((*act)->doente.id) != atoi(id)) *act = NULL;
}

void insere(lista l, Doente d){
    lista no, ant, inutil;
    no = (lista)malloc(sizeof(node));
    if (no != NULL){
        no->doente = d;
        no->maior_tensao = 0;
        no->registo_doente = cria_registo();
        procura(l,d.nome, &ant, &inutil);
        no->prox = ant->prox;
        ant->prox = no;
    }
}

int verifica_data(data d1, data d2){
    if(atoi(d1.ano) > atoi(d2.ano))return 1;
    else if(atoi(d1.ano) < atoi(d2.ano))return 0;
    if(atoi(d1.mes) > atoi(d2.mes))return 1;
    else if(atoi(d1.mes) < atoi(d2.mes))return 0;
    if(atoi(d1.dia) > atoi(d2.dia))return 1;
    else if(atoi(d1.dia) < atoi(d2.dia))return 0;
    else return 0;
}

void procura_data(lista_reg l, data d, lista_reg *ant, lista_reg *act){
    *ant = l;
    *act = l->prox;
    while ((*act)!=NULL && verifica_data((*act)->registo.data_r,d) && !vazia_registo(l)){
        *ant = *act;
        *act = (*act)->prox;
    }   
}

void insere_registo(lista_reg l, Registo r){
    lista_reg no, ant, inutil;
    no = (lista_reg)malloc(sizeof(node_reg));
    if (no != NULL){
        no->registo = r;
        procura_data(l, r.data_r, &ant, &inutil);
        no->prox = ant->prox;
        ant->prox = no;
    }
}

void novo_registo(lista l){
    Registo registo_novo;
    lista paciente;
    char nome[50];
    char x = getchar();
    int dia, mes, ano, num;
    printf("\nNome:\n--> ");
    scanf("%[^\n]",nome);
    paciente = pesquisa(l, nome);
    if(paciente != NULL){
        while (1) {
            printf("\nData (DD/MM/AAAA):\n--> ");
            scanf("%2s/%2s/%4s", registo_novo.data_r.dia, registo_novo.data_r.mes, registo_novo.data_r.ano);
            dia = atoi(registo_novo.data_r.dia);
            mes = atoi(registo_novo.data_r.mes);
            ano = atoi(registo_novo.data_r.ano);
            if (dataValida(dia, mes, ano)) break;
            else printf("Data inválida! Por favor, tente novamente.\n");
        }
        printf("\nTensão Máxima:\n--> ");
        scanf("%s", registo_novo.tensao_maxima);
        if(paciente->maior_tensao<atoi(registo_novo.tensao_maxima))paciente->maior_tensao = atoi(registo_novo.tensao_maxima);
        printf("\nTensão Miníma:\n--> ");
        scanf("%s", registo_novo.tensao_minima);
        printf("\nPeso:\n--> ");
        scanf("%s", registo_novo.peso);
        printf("\nAltura:\n--> ");
        scanf("%s", registo_novo.altura);
        insere_registo(paciente->registo_doente, registo_novo);
    }
    else printf("\nNome não identificado!\nIntroduza novamente\n");
}

void novo_doente(lista l, int c){
    Doente novo;
    sprintf(novo.id, "%d", c);
    char x = getchar();
    int dia, mes, ano;
    printf("\nNome:\n--> ");
    scanf("%[^\n]",novo.nome);
    if (pesquisa(l,novo.nome) != NULL){
        printf("\nNome já existente!\nIntroduza novamente\n");
        return;
    }
    while (1) {
        printf("\nData de Nascimento (DD/MM/AAAA):\n--> ");
        if (scanf("%2s/%2s/%4s", novo.data_n.dia, novo.data_n.mes, novo.data_n.ano) != 3) {
            printf("Data inválida! Por favor, tente novamente.\n");
            while(getchar() != '\n');
            continue;
        }
        dia = atoi(novo.data_n.dia);
        mes = atoi(novo.data_n.mes);
        ano = atoi(novo.data_n.ano);
        if (dataValida(dia, mes, ano)) break;
        printf("Data inválida! Por favor, tente novamente.\n");
    }
    while (1) {
        printf("\nNúmero de Cartão de Cidadão (XXXXXXXX-X-AAX):\n--> ");
        scanf("%s",novo.cc);
        if (ccValido(novo.cc)) break;
        else printf("Número de Cartão de Cidadão inválido, tente novamente.\n");
    }
    if (islower(novo.cc[11])) novo.cc[11] = toupper(novo.cc[11]);
    if (islower(novo.cc[12])) novo.cc[12] = toupper(novo.cc[12]);
    while (1) {
        printf("\nNúmero de Telemóvel (XXXXXXXXX):\n--> ");
        scanf("%s",novo.num);
        if (telefoneValido(novo.num)) break;
        else printf("Número de telemóvel inválido, tente novamente.\n");
    }
    while (1) {
        printf("\ne-mail (exemplo@exemplo.com):\n--> ");
        scanf("%s",novo.mail);
        if (emailValido(novo.mail)) break;
        else printf("Email inválido, tente novamente.\n");
    }
    insere(l,novo);
    format_nome(novo.nome);
    printf("\nFoi introduzido o doente %s - ID: %s\n",novo.nome,novo.id);
}

void elimina(lista l, char nome[]){
    lista ant, act;
    procura(l,nome,&ant,&act);
    if (act != NULL){
        format_nome(act->doente.nome);
        printf("\nFoi eliminado o doente %s - ID: %s\n", act->doente.nome, act->doente.id);
        ant->prox = act->prox;
        destroi_reg(act->registo_doente);
        free(act);
    }
    else printf("\nNome não identificado!\nIntroduza novamente\n");
}

void elimina_doente(lista l){
    char nome[50];
    char x = getchar();
    printf("\nNome:\n--> ");
    scanf("%[^\n]",nome);
    elimina(l, nome);
}

lista pesquisa(lista l, char nome[]){
    lista ant, act;
    procura(l, nome, &ant, &act);
    return act;
}

lista pesquisa_id(lista l, char id[]){
    lista ant, act;
    procura_id(l, id, &ant, &act);
    return act;
}

void imprime(lista l){
    int id;
    lista aux = l->prox;
    if (!vazia(l)){
        printf("\nID - Nome\n");
        while(aux!=NULL){
            format_nome(aux->doente.nome);
            id = atoi(aux->doente.id);
            printf("%02d - %s\n", id, aux->doente.nome);
            aux = aux->prox;
        }
    }
    else printf("\nA lista de pacientes está vazia!\n");
}

void imprime_registo(lista_reg l){
    if (!vazia_registo(l)){
        lista_reg aux = l->prox;
        while(aux!=NULL){
            printf("\nData: %s/%s/%s", aux->registo.data_r.dia,aux->registo.data_r.mes,aux->registo.data_r.ano);
            printf("\nTensão máxima: %s", aux->registo.tensao_maxima);
            printf("\nTensão miníma: %s", aux->registo.tensao_minima);
            printf("\nAltura: %s cm", aux->registo.altura);
            printf("\nPeso: %s kg\n", aux->registo.peso);
            aux = aux->prox;
        }
    }
    else printf("\nEste paciente ainda não tem registos\n");
}

void imprime_doente (lista l){
    if (!vazia(l)){
        char nome[50];
        char x = getchar();
        printf("\nNome:\n--> ");
        scanf("%[^\n]", nome);
        lista paciente = pesquisa(l, nome);
        if (paciente != NULL) {
            printf("\nInformações do paciente:\n");
            printf("\nID: %s\n", paciente->doente.id);
            format_nome(paciente->doente.nome);
            printf("Nome: %s\n", paciente->doente.nome);
            printf("Data de Nascimento: %s/%s/%s\n", paciente->doente.data_n.dia, paciente->doente.data_n.mes, paciente->doente.data_n.ano);
            printf("Número de Cartão de Cidadão: %s\n", paciente->doente.cc);
            printf("Número de Telemóvel: %s\n", paciente->doente.num);
            printf("E-mail: %s\n", paciente->doente.mail);
            imprime_registo(paciente->registo_doente);
        } 
        else printf("\nPaciente não encontrado.\n");
    }
    else printf("\nA lista de pacientes está vazia!\n");
}

void procura_tensao(lista_ten l, int t, lista_ten *ant, lista_ten *act){
    *ant = l;
    *act = l->prox;
    while ((*act)!=NULL && (*act)->doente.tensao>=t && !vazia_tensao(l)){
        *ant = *act;
        *act = (*act)->prox;
    }
}

void insere_tensoes(lista_ten l, nome_ten t){
    lista_ten no, ant, inutil;
    no = (lista_ten)malloc(sizeof(node_tensao));
    if (no != NULL){
        no->doente = t;
        procura_tensao(l,t.tensao, &ant, &inutil);
        no->prox = ant->prox;
        ant->prox = no;
    }
}

void lista_tensoes(lista l, int t, lista_ten l_t){
    lista aux = l->prox;
    while(aux!=NULL){
        if(aux->maior_tensao >= t){
            nome_ten nova_tensao;
            nova_tensao.tensao = aux->maior_tensao;
            strcpy(nova_tensao.nome,aux->doente.nome);
            insere_tensoes(l_t, nova_tensao);
        }
        aux = aux->prox;
    }
}

void imprime_nome_tensoes(lista l, int t, lista_ten l_t){
    lista_tensoes(l, t, l_t);
    if (!vazia_tensao(l_t)){
        lista_ten aux = l_t->prox;
        printf("\nTensão - Nome\n");
        while(aux!=NULL){
            format_nome(aux->doente.nome);
            printf(" %.3d   - %s\n", aux->doente.tensao, aux->doente.nome);
            aux = aux->prox;
        }
    }
    else printf("\nNão havia Doentes com tensões maiores que a desejada\n");
}

void imprime_tensoes(lista l){
    if (!vazia(l)){
        lista_ten l_t = cria_tensoes();
        int tensao;
        printf("\nTensão desejada:\n--> ");
        scanf("%d",&tensao);
        imprime_nome_tensoes(l, tensao, l_t);
        destroi_ten(l_t);
    }
    else printf("\nA lista de pacientes está vazia!\n");
}

void le_doente_fich(lista l, int *contador) {
    FILE *f;
    Doente doente;
    char temp[100];
    f = fopen("doentes.txt", "r");
    if (f == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return;
    }
    while (fgets(temp, sizeof(temp), f) != NULL) {
        sscanf(temp, "%s", doente.id);
        fgets(temp, sizeof(temp), f);
        sscanf(temp, "%[^\n]", doente.nome);
        fgets(temp, sizeof(temp), f);
        sscanf(temp, "%2s/%2s/%4s", doente.data_n.dia, doente.data_n.mes, doente.data_n.ano);
        fgets(temp, sizeof(temp), f);
        sscanf(temp, "%s", doente.cc);
        fgets(temp, sizeof(temp), f);
        sscanf(temp, "%s", doente.num);
        fgets(temp, sizeof(temp), f);
        sscanf(temp, "%s", doente.mail);
        if (*contador<=atoi(doente.id)) *contador = atoi(doente.id) + 1; 
        insere(l, doente);
    }
    fclose(f);
}

void le_registo_fish(lista l){
    FILE *f;
    Registo registo;
    char temp[100];
    f = fopen("registos.txt", "r");
    if (f == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return;
    }
    while (fgets(temp, sizeof(temp), f) != NULL) {
        lista paciente = pesquisa_id(l, temp);
        if (paciente != NULL) {
            fgets(temp, sizeof(temp), f);
            sscanf(temp, "%2s/%2s/%4s", registo.data_r.dia, registo.data_r.mes, registo.data_r.ano);
            fgets(temp, sizeof(temp), f);
            sscanf(temp, "%s", registo.tensao_maxima);
            if(paciente->maior_tensao<atoi(registo.tensao_maxima))paciente->maior_tensao = atoi(registo.tensao_maxima);
            fgets(temp, sizeof(temp), f);
            sscanf(temp, "%s", registo.tensao_minima);
            fgets(temp, sizeof(temp), f);
            sscanf(temp, "%s", registo.peso);
            fgets(temp, sizeof(temp), f);
            sscanf(temp, "%s", registo.altura);
            insere_registo(paciente->registo_doente, registo);
        }
        else{
            fscanf(f, "%s", temp);
            fscanf(f, "%s", temp);
            fscanf(f, "%s", temp);
            fscanf(f, "%s", temp);
            fscanf(f, "%s", temp);
            //caso não exista o doente salta para a linha do próximo id
        }
    }
    fclose(f);
}

void insere_doente_fich(lista l) {
    FILE *f;
    f = fopen("doentes.txt", "w");
    if (f == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return;
    }
    lista aux = l->prox;
    while (aux != NULL) {
        fprintf(f, "%s\n", aux->doente.id);
        fprintf(f, "%s\n", aux->doente.nome);
        fprintf(f, "%s/%s/%s\n", aux->doente.data_n.dia, aux->doente.data_n.mes, aux->doente.data_n.ano);
        fprintf(f, "%s\n", aux->doente.cc);
        fprintf(f, "%s\n", aux->doente.num);
        fprintf(f, "%s\n", aux->doente.mail);
        aux = aux->prox;
    }
    fclose(f);
}

void insere_registo_fich(lista l){
    FILE *f;
    f = fopen("registos.txt", "w");
    if (f == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return;
    }
    lista aux = l->prox;
    while (aux != NULL) {
        lista_reg aux_reg = aux->registo_doente->prox;
        while (aux_reg != NULL) {
            fprintf(f, "%s\n", aux->doente.id);
            fprintf(f, "%s/%s/%s\n", aux_reg->registo.data_r.dia, aux_reg->registo.data_r.mes, aux_reg->registo.data_r.ano);
            fprintf(f, "%s\n", aux_reg->registo.tensao_maxima);
            fprintf(f, "%s\n", aux_reg->registo.tensao_minima);
            fprintf(f, "%s\n", aux_reg->registo.peso);
            fprintf(f, "%s\n", aux_reg->registo.altura);
            aux_reg = aux_reg->prox;
        }
        aux = aux->prox;
    }
    fclose(f);
}

int ehBissexto(int ano) {
    return (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
}

int dataValida(int dia, int mes, int ano) {
    if (ano < 1900 || mes < 1 || mes > 12 || dia < 1 || dia > 31) return 0;
    if (mes == 2) {
        if (ehBissexto(ano)) {
            if (dia > 29) return 0;
        } else {
            if (dia > 28) return 0; 
        }
    } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
        if (dia > 30) return 0; 
    }
    return 1; 
}

int telefoneValido(const char* telefone) {
    if (strlen(telefone) != 9) return 0;
    for (int i = 0; i < 9; i++) {
        if (!isdigit(telefone[i])) return 0;
    }
    return 1; 
}

int ccValido(const char* cc) {
    if (strlen(cc) != 14) return 0;
    for (int i = 0; i < 8; i++) {
        if (!isdigit(cc[i])) return 0;
    }
    if (cc[8] != '-' || cc[10] != '-') return 0;
    if (!isdigit(cc[9])) return 0;
    for (int i = 11; i < 13; i++) {
        if (isdigit(cc[i])) return 0; 
    }
    if (!isdigit(cc[13]))return 0;
    return 1; 
}

int emailValido(const char* email){
    int i = 0;
    int j = 0, contador = 0;
    while (email[j] != '\0'){
        if (email[j] == '@') contador++;
        j++;
    }
    if (contador != 1) return 0;
    while (email[i] != '@' && email[i] != '\0') i++;
    i++;
    if (email[i] == '\0') return 0;
    while (email[i] != '.' && email[i] != '\0') i++;
    if (email[i+1] == '\0') return 0;
    return 1;
}


    