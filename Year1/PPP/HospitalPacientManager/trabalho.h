#ifndef TRABALHO_H
#define TRABALHO_H

#include <stdio.h>

typedef struct {
    char dia[3];
    char mes[3];
    char ano[5];
} data;

typedef struct {
    char id[6];
    char nome[50];
    data data_n; 
    char cc[15];
    char num[10];
    char mail[50];
} Doente;

typedef struct {
    data data_r;
    char tensao_maxima[5];
    char tensao_minima[5];
    char peso[5];
    char altura[5];
} Registo;

typedef struct{
    char nome[50];
    int tensao;
}nome_ten;

typedef struct node_reg{
    Registo registo;
    struct node_reg *prox;
}node_reg;

typedef node_reg * lista_reg;

typedef struct node{
    Doente doente;
    lista_reg registo_doente;
    int maior_tensao;
    struct node *prox;
}node;

typedef node * lista;

typedef struct node_tensao{
    nome_ten doente;
    struct node_tensao *prox;
}node_tensao;

typedef node_tensao * lista_ten;

void menu();

lista cria();

lista_reg cria_registo();

lista_ten cria_tensoes();

int vazia(lista l);

int vazia_registo(lista_reg l);

int vazia_tensao(lista_ten l);

lista destroi(lista l);

lista_reg destroi_reg(lista_reg l);

lista_ten destroi_ten(lista_ten l);

void minus(char n1[],char n2[],int tam1,int tam2);

void format_nome(char n[]);

int verifica_alfabeto(char n1[], char n2[]);

void procura(lista l, char nome[], lista *ant, lista *act);

void procura_id(lista l, char id[], lista *ant, lista *act);

void insere(lista l, Doente d);

int verifica_data(data d1, data d2);

void procura_data(lista_reg l, data d, lista_reg *ant, lista_reg *act);

void insere_registo(lista_reg l, Registo r);

void novo_registo(lista l);

void novo_doente(lista l, int c);

void elimina(lista l, char nome[]);

void elimina_doente(lista l);

lista pesquisa(lista l, char nome[]);

lista pesquisa_id(lista l, char id[]);

void imprime(lista l);

void imprime_registo(lista_reg l);

void imprime_doente(lista l);

void procura_tensao(lista_ten l, int t, lista_ten *ant, lista_ten *act);

void insere_tensoes(lista_ten l, nome_ten t);

void lista_tensoes(lista l, int t, lista_ten l_t);

void imprime_nome_tensoes(lista l, int t, lista_ten l_t);

void imprime_tensoes(lista l);

void le_doente_fich(lista l, int *contador);

void le_registo_fish(lista l);

void insere_doente_fich(lista l);

void insere_registo_fich(lista l);

int ehBissexto(int ano);

int dataValida(int dia, int mes, int ano);

int telefoneValido(const char* telefone);

int ccValido(const char* cc);

int emailValido(const char* email);

#endif