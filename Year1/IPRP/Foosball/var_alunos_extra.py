import foosball_alunos_extra

def le_replay(nome_ficheiro):
    '''
    Função que recebe o nome de um ficheiro contendo um replay, e que deverá 
    retornar um dicionário com as seguintes chaves:
    bola - lista contendo tuplos com as coordenadas xx e yy da bola
    jogador_vermelho - lista contendo tuplos com as coordenadas xx e yy da do jogador\_vermelho
    jogador_azul - lista contendo tuplos com as coordenadas xx e yy da do jogador\_azul
    '''
    with open(nome_ficheiro,"r") as ficheiro:
        bola = []
        jogador_vermelho = []
        jogador_azul = []
        b = ficheiro.readline().replace("\n","")
        j_v = ficheiro.readline().replace("\n","")
        j_a = ficheiro.readline().replace("\n","")
        b_coor = b.split(";")
        j_v_coor = j_v.split(";")
        j_a_coor = j_a.split(";")
        for i in b_coor:
            coor = i.split(",")
            coor[0] = float(coor[0])
            coor[1] = float(coor[1])
            coor = tuple(coor)
            bola.append(coor)
        for i in j_v_coor:
            coor = i.split(",")
            coor[0] = float(coor[0])
            coor[1] = float(coor[1])
            coor = tuple(coor)            
            jogador_vermelho.append(coor)
        for i in j_a_coor:
            coor = i.split(",")
            coor[0] = float(coor[0])
            coor[1] = float(coor[1])
            coor = tuple(coor)            
            jogador_azul.append(coor)
        ficheiro.close()
    return {"bola":bola,"jogador_vermelho":jogador_vermelho,"jogador_azul":jogador_azul}
        
def main():
    estado_jogo = foosball_alunos_extra.init_state()
    foosball_alunos_extra.setup(estado_jogo, False)
    replay = le_replay('replay_golo_jv_3_ja_1.txt')
    for i in range(len(replay['bola'])):
        estado_jogo['janela'].update()
        estado_jogo['jogador_vermelho'].pu()
        estado_jogo['jogador_azul'].pu()
        estado_jogo['bola']['objecto'].pu()        
        estado_jogo['jogador_vermelho'].setpos(replay['jogador_vermelho'][i])
        estado_jogo['jogador_azul'].setpos(replay['jogador_azul'][i])
        estado_jogo['bola']['objecto'].setpos(replay['bola'][i])
    estado_jogo['janela'].exitonclick()


if __name__ == '__main__':
    main()
