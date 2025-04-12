#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "trabalho.h"

int main(){
    lista lista_doentes = cria();
    int opcao;
    Doente novo; 
    int contador = 1;
    if (access("doentes.txt", 0) == 0){
        le_doente_fich(lista_doentes, &contador);
        if (access("registos.txt", 0) == 0)le_registo_fish(lista_doentes);
    }
    do{
        menu();
        scanf("%d",&opcao);
        switch (opcao){
        case 1:
            novo_doente(lista_doentes, contador);
            contador++;
            break;
        case 2:
            elimina_doente(lista_doentes);
            break;
        case 3:
            imprime (lista_doentes);
            break;
        case 4:
            imprime_tensoes(lista_doentes);
            break;
        case 5:
            imprime_doente(lista_doentes);
            break;
        case 6:
            novo_registo(lista_doentes);
            break;
        case 0:
            printf("Muito obrigado, até breve!\n");
            insere_registo_fich(lista_doentes);
            insere_doente_fich(lista_doentes);
            break;
        default:
            printf("\n¡ATENÇÃO!\nNão escolheu nenhuma das opcões, por favor degite uma nova opção\n");
            break;
        }
    }while(opcao!=0);
    lista_doentes = destroi(lista_doentes);
    return 0;
}