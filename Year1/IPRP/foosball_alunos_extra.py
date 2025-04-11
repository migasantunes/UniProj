import turtle as t
import functools
import random
import time

LARGURA_JANELA = 1024
ALTURA_JANELA = 600
DEFAULT_TURTLE_SIZE = 40
DEFAULT_TURTLE_SCALE = 3
RAIO_JOGADOR = DEFAULT_TURTLE_SIZE / DEFAULT_TURTLE_SCALE
RAIO_BOLA = DEFAULT_TURTLE_SIZE / 2
PIXEIS_MOVIMENTO = 90
LADO_MAIOR_AREA = ALTURA_JANELA / 3
LADO_MENOR_AREA = 50
RAIO_MEIO_CAMPO = LADO_MAIOR_AREA / 2 #mudei para divisao de 2 em vez de 4, para coincidir com a imagem no pdf sobre o trabalho
START_POS_BALIZAS = ALTURA_JANELA / 4
BOLA_START_POS = (5,5)
HITBOX_J = RAIO_JOGADOR + 15 #adicionei 15 para evitar o erro da janela, pois sobrepõe mesmo estando as coordenadas dentro
HITBOX_B = 10 
DIRECAO = [-0.7,-0.6,-0.5,0.5,0.6,0.7]
pausa_conta = 1
inicio = time.time() #temporizador
demora = []

def vp(tartaruga,x,y):
    tartaruga.pu()
    tartaruga.goto(x,y)
    tartaruga.pd()
    
# Funções responsáveis pelo movimento dos jogadores no ambiente.
# O número de unidades que o jogador se pode movimentar é definida pela constante PIXEIS_MOVIMENTO.
# As funções recebem um dicionário que contém o estado do jogo e o jogador que se está a movimentar. 

#tentativa de fazer um botao pausa
def pausa(janela):
    global inicio
    global pausa_conta
    global demora
    duracao_total = 0
    pausa_conta += 1
    pausa = t.Turtle()
    pausa.ht()
    vp(pausa,1,-60)
    while pausa_conta%2==0:
        inicio_conta = time.time()
        pausa.color("#B9B9B9")
        pausa.write("PAUSA",False,"center",("Bold",80))
        time.sleep(0.1)
        pausa.clear()
        pausa.color("#959494")
        pausa.write("PAUSA",False,"center",("Bold",80))
        time.sleep(0.1)
        pausa.clear()
        fim_conta = time.time()-inicio_conta
        demora.append(fim_conta)
    if pausa_conta%2!=0:
        for n in demora:
            duracao_total += n
        inicio += duracao_total
        duracao_total = 0
        demora = []
        pausa.clear()
    
def jogador_cima(estado_jogo, jogador):
    jogador_t = estado_jogo[jogador]
    jogador_t.pu()
    jogador_t.seth(90)
    if jogador_t.ycor()+PIXEIS_MOVIMENTO>ALTURA_JANELA/2-HITBOX_J:
        MOVIMENTO_NOVO = ALTURA_JANELA/2 - jogador_t.ycor() - HITBOX_J
        jogador_t.fd(MOVIMENTO_NOVO)
    else:
        jogador_t.fd(PIXEIS_MOVIMENTO)
    jogador_t.pd()

def jogador_baixo(estado_jogo, jogador):
    jogador_t = estado_jogo[jogador]
    jogador_t.pu()
    jogador_t.seth(-90)
    if jogador_t.ycor()-PIXEIS_MOVIMENTO<-ALTURA_JANELA/2+HITBOX_J:
        MOVIMENTO_NOVO = ALTURA_JANELA/2 + jogador_t.ycor() - HITBOX_J
        jogador_t.fd(MOVIMENTO_NOVO)
    else:
        jogador_t.fd(PIXEIS_MOVIMENTO)
    jogador_t.pd()
    
def jogador_direita(estado_jogo, jogador):
    jogador_t = estado_jogo[jogador]
    jogador_t.pu()
    jogador_t.seth(0)
    if jogador_t.xcor()+PIXEIS_MOVIMENTO>LARGURA_JANELA/2-HITBOX_J:
        MOVIMENTO_NOVO = LARGURA_JANELA/2 - jogador_t.xcor() - HITBOX_J
        jogador_t.fd(MOVIMENTO_NOVO)
    else:
        jogador_t.fd(PIXEIS_MOVIMENTO)
    jogador_t.pd()

def jogador_esquerda(estado_jogo, jogador):
    jogador_t = estado_jogo[jogador]
    jogador_t.pu()
    jogador_t.seth(180)
    if jogador_t.xcor()-PIXEIS_MOVIMENTO<-LARGURA_JANELA/2+HITBOX_J:
        MOVIMENTO_NOVO = LARGURA_JANELA/2 + jogador_t.xcor() - HITBOX_J
        jogador_t.fd(MOVIMENTO_NOVO)
    else:
        jogador_t.fd(PIXEIS_MOVIMENTO)
    jogador_t.pd()

def placar(linhas): #desenha o quadro do placar
    linhas.pencolor("black")
    linhas.fillcolor("#71D877")
    linhas.begin_fill()
    for n in range (2):
        linhas.fd(230)
        linhas.lt(90)
        linhas.fd(45)
        linhas.lt(90)
    linhas.end_fill()

def placar_tempo(linhas): #desenha o quadro do temporizador
    linhas.pencolor("black")
    linhas.fillcolor("#71D877")
    linhas.begin_fill()
    for n in range (2):
        linhas.fd(110)
        linhas.lt(90)
        linhas.fd(45)
        linhas.lt(90)
    linhas.end_fill()
    
def desenha_linhas_campo(jogar):
    ''' Função responsável por desenhar as linhas do campo, 
    nomeadamente a linha de meio campo, o círculo central, e as balizas. '''
    def baliza():
        linhas.fd(LADO_MENOR_AREA)
        linhas.lt(90)
        linhas.fd(LADO_MAIOR_AREA)
        linhas.lt(90)
        linhas.fd(LADO_MENOR_AREA)    
    linhas = t.Turtle()
    linhas.ht()
    linhas.pencolor("white")
    linhas.width(7)
    vp(linhas,0,-6)
    linhas.circle(6)
    vp(linhas,0,-RAIO_MEIO_CAMPO) 
    linhas.circle(RAIO_MEIO_CAMPO)
    vp(linhas,0,-ALTURA_JANELA/2)
    linhas.goto(0,ALTURA_JANELA/2)
    vp(linhas,-LARGURA_JANELA/2,-LADO_MAIOR_AREA/2)
    baliza()
    vp(linhas,LARGURA_JANELA/2,LADO_MAIOR_AREA/2)
    baliza()
    if jogar: #daqui até placar_tempo(linhas) desenha o quadro do temporizador e o quadro do placar e tive que modificar metendo if jogar pois no replay não aparece placar
        linhas.width(2) 
        vp(linhas,-430,260)
        placar(linhas)
        vp(linhas,177,260)
        placar(linhas)
        vp(linhas,-55,260)
        placar_tempo(linhas)


def criar_bola():
    '''
    Função responsável pela criação da bola. 
    Deverá considerar que esta tem uma forma redonda, é de cor preta, 
    começa na posição BOLA_START_POS com uma direção aleatória. 
    Deverá ter em conta que a velocidade da bola deverá ser superior à dos jogadores. 
    A função deverá devolver um dicionário contendo 4 elementos: o objeto bola, 
    a sua direção no eixo dos xx, a sua direção no eixo dos yy, 
    e um elemento inicialmente a None que corresponde à posição anterior da mesma.
    '''
    xx = 0
    yy = 0
    bola = t.Turtle()
    bola.shape("circle")
    bola.pensize(RAIO_BOLA)
    vp(bola,5,5) #(5,5) = BOLA_START_POS
    while xx == yy or xx == -yy:
        xx,yy = random.choice(DIRECAO),random.choice(DIRECAO)
    return {"objecto":bola,"xx":xx,"yy":yy,"pos_ant": None}

def cria_jogador(x_pos_inicial, y_pos_inicial, cor):
    ''' Função responsável por criar e devolver o objeto que corresponde a um jogador (um objecto Turtle). 
    A função recebe 3 argumentos que correspondem às coordenadas da posição inicial 
    em xx e yy, e a cor do jogador. A forma dos jogadores deverá ser um círculo, 
    cujo seu tamanho deverá ser definido através da função shapesize
    do módulo \texttt{turtle}, usando os seguintes parâmetros: 
    stretch_wid=DEFAULT_TURTLE_SCALE, stretch_len=DEFAULT_TURTLE_SCALE. '''
    jogador = t.Turtle()  
    jogador.color(cor)
    jogador.shape("circle")
    jogador.shapesize(DEFAULT_TURTLE_SCALE,DEFAULT_TURTLE_SCALE)
    vp(jogador,x_pos_inicial, y_pos_inicial)
    return jogador


def init_state():
    estado_jogo = {}
    estado_jogo['bola'] = None
    estado_jogo['jogador_vermelho'] = None
    estado_jogo['jogador_azul'] = None
    estado_jogo['var'] = {
        'bola' : [],
        'jogador_vermelho' : [],
        'jogador_azul' : [],
    }
    estado_jogo['pontuacao_jogador_vermelho'] = 0
    estado_jogo['pontuacao_jogador_azul'] = 0
    return estado_jogo

def cria_janela():
    #create a window and declare a variable called window and call the screen()
    window=t.Screen()
    window.title("Foosball Game")
    window.bgcolor("green")
    window.setup(width = LARGURA_JANELA,height = ALTURA_JANELA)
    window.tracer(0)
    return window

def cria_quadro_resultados():
    #Code for creating pen for scorecard update
    quadro=t.Turtle()
    quadro.speed(0)
    quadro.color("black")
    quadro.penup()
    quadro.hideturtle()
    quadro.goto(0,260)
    quadro.write("Player A:{:2.0f}\t\t\tPlayer B:{:2.0f} ".format(0,0), align="center", font=('Monaco',24,"normal"))
    return quadro


def terminar_jogo(estado_jogo):
    '''
     Função responsável por terminar o jogo. Nesta função, deverá atualizar o ficheiro 
     ''historico_resultados.csv'' com o número total de jogos até ao momento, 
     e o resultado final do jogo. Caso o ficheiro não exista, 
     ele deverá ser criado com o seguinte cabeçalho: 
     NJogo,JogadorVermelho,JogadorAzul.
    '''
    inicio = estado_jogo["inicio_jogo"]
    temporizador = time.time()-inicio
    minutos = "{:02.0f}".format(temporizador//60)
    segundos = "{:02.0f}".format(temporizador%60)    
    with open("historico_resultados_extra.csv","r+") as resultado:
        n_jogos = 0
        for n in resultado.read():
            if n == "\n":
                n_jogos += 1
        resultados_jogos = str(n_jogos)+","+str(estado_jogo['pontuacao_jogador_vermelho'])+","+str(estado_jogo['pontuacao_jogador_azul'])+","+str(minutos+":"+segundos)+"\n" # adicionei o tempo que o jogo demorou
        resultado.write(resultados_jogos)
        resultado.close()
    print("Adeus")
    estado_jogo['janela'].bye()

def setup(estado_jogo, jogar):
    janela = cria_janela()
    #Assign keys to play
    janela.listen()
    desenha_linhas_campo(jogar) #modifiquei, metendo para aqui esta função de modo a que o placar esteja por baixo do quadro de resultados
    if jogar:
        janela.onkeypress(functools.partial(jogador_cima, estado_jogo, 'jogador_vermelho') ,'w')
        janela.onkeypress(functools.partial(jogador_baixo, estado_jogo, 'jogador_vermelho') ,'s')
        janela.onkeypress(functools.partial(jogador_esquerda, estado_jogo, 'jogador_vermelho') ,'a')
        janela.onkeypress(functools.partial(jogador_direita, estado_jogo, 'jogador_vermelho') ,'d')
        janela.onkeypress(functools.partial(jogador_cima, estado_jogo, 'jogador_azul') ,'Up')
        janela.onkeypress(functools.partial(jogador_baixo, estado_jogo, 'jogador_azul') ,'Down')
        janela.onkeypress(functools.partial(jogador_esquerda, estado_jogo, 'jogador_azul') ,'Left')
        janela.onkeypress(functools.partial(jogador_direita, estado_jogo, 'jogador_azul') ,'Right')
        janela.onkeypress(functools.partial(pausa, janela) ,'p') #botao pausa
        janela.onkeypress(functools.partial(terminar_jogo, estado_jogo) ,'Escape')
        quadro = cria_quadro_resultados()
        estado_jogo['quadro'] = quadro
    bola = criar_bola()
    jogador_vermelho = cria_jogador(-((ALTURA_JANELA / 2) + LADO_MENOR_AREA), 0, "red")
    jogador_azul = cria_jogador(((ALTURA_JANELA / 2) + LADO_MENOR_AREA), 0, "blue")
    estado_jogo['janela'] = janela
    estado_jogo['bola'] = bola
    estado_jogo['jogador_vermelho'] = jogador_vermelho
    estado_jogo['jogador_azul'] = jogador_azul
    guarda_posicoes_para_var(estado_jogo)

def update_board(estado_jogo):
    estado_jogo['quadro'].clear()
    estado_jogo['quadro'].write("Player A:{:2.0f}\t\t\tPlayer B:{:2.0f} ".format(estado_jogo['pontuacao_jogador_vermelho'], estado_jogo['pontuacao_jogador_azul']),align="center",font=('Monaco',24,"normal")) # fiz com que ao ter 10 pontos se encoste ao ":"

def movimenta_bola(estado_jogo):
    '''
    Função responsável pelo movimento da bola que deverá ser feito tendo em conta a posição atual da bola e a direção em xx e yy.
    '''
    b = estado_jogo["bola"]
    bola = b["objecto"]
    b["pos_ant"] = [bola.xcor(), bola.ycor()]
    bola.pu()
    bola.goto(b["pos_ant"][0]+b["xx"],b["pos_ant"][1]+b["yy"])
    bola.pd()
    guarda_posicoes_para_var(estado_jogo)

def verifica_colisoes_ambiente(estado_jogo):
    '''
    Função responsável por verificar se há colisões com os limites do ambiente, 
    atualizando a direção da bola. Não se esqueça de considerar que nas laterais, 
    fora da zona das balizas, a bola deverá inverter a direção onde atingiu o limite.
    '''
    b = estado_jogo["bola"]
    bola = b["objecto"]
    if abs(b["pos_ant"][1]+b["yy"])>abs(ALTURA_JANELA/2-HITBOX_B): #Limite Superior e Inferior
        b["yy"] *= -1
    if abs(b["pos_ant"][0]+b["xx"])>abs(LARGURA_JANELA/2-HITBOX_B): #Lateral Direita e Esquerda
        b["xx"] *= -1

def doc_coordenadas_format(estado_jogo,objeto): #Função que formata as coordenadas que iram passar para o documento var de cada golo
    n = 1
    coordenadas = ""
    for p in str(estado_jogo["var"][objeto]):
        if p == "[" or p == "]" or p == ")" or p == "(" or p == " ":
            coordenadas += ""
        elif p == ",":
            if n%2==0:
                coordenadas += ";"
            else:
                coordenadas += p
            n += 1
        else:
            coordenadas += p
    return coordenadas

def foi_golo(cor): #Função que escreve golo quando algum jogador marca, a cor da escrita é a cor de quem marca
    global inicio
    global demora    
    duracao_total = 0    
    golo = t.Turtle()
    golo.ht()
    vp(golo,-9,-50)
    for n in range (5):
        inicio_conta = time.time()        
        golo.color(cor)
        golo.write("GOLOOOO!",False,"center",("Bold",65))
        time.sleep(0.1)
        golo.clear()
        golo.color("#CFD5D5")
        golo.write("GOLOOOO!",False,"center",("Bold",65))
        time.sleep(0.1)
        golo.clear()
        fim_conta = time.time()-inicio_conta
        demora.append(fim_conta)  
    for n in demora:
        duracao_total += n
    inicio += duracao_total
    duracao_total = 0
    demora = []    
    golo.clear()

def verifica_golo_jogador_vermelho(estado_jogo):
    '''
    Função responsável por verificar se um determinado jogador marcou golo. 
    Para fazer esta verificação poderá fazer uso das constantes: 
    LADO_MAIOR_AREA e 
    START_POS_BALIZAS. 
    Note que sempre que há um golo, deverá atualizar a pontuação do jogador, 
    criar um ficheiro que permita fazer a análise da jogada pelo VAR, 
    e reiniciar o jogo com a bola ao centro. 
    O ficheiro para o VAR deverá conter todas as informações necessárias 
    para repetir a jogada, usando as informações disponíveis no objeto 
    estado_jogo['var']. O ficheiro deverá ter o nome 
    
    replay_golo_jv_[TotalGolosJogadorVermelho]_ja_[TotalGolosJogadorAzul].txt 
    
    onde [TotalGolosJogadorVermelho], [TotalGolosJogadorAzul] 
    deverão ser substituídos pelo número de golos marcados pelo jogador vermelho 
    e azul, respectivamente. Este ficheiro deverá conter 3 linhas, estruturadas 
    da seguinte forma:
    Linha 1 - coordenadas da bola;
    Linha 2 - coordenadas do jogador vermelho;
    Linha 3 - coordenadas do jogador azul;
    
    Em cada linha, os valores de xx e yy das coordenadas são separados por uma 
    ',', e cada coordenada é separada por um ';'.
    '''
    b = estado_jogo["bola"]
    bola = b["objecto"]
    if b["pos_ant"][0]+b["xx"]+1.5>LARGURA_JANELA/2-HITBOX_B: #+1.5 para evitar a margem de erro senão não é lido que se marca golo
        if -LADO_MAIOR_AREA/2<b["pos_ant"][1]<LADO_MAIOR_AREA/2:
            estado_jogo['pontuacao_jogador_vermelho'] += 1
            update_board(estado_jogo)
            bola.ht()            
            estado_jogo['bola'] = None
            bola = criar_bola()
            bola["pos_ant"] = (5,5)
            estado_jogo['bola'] = bola
            with open("replay_golo_jv_"+str(estado_jogo["pontuacao_jogador_vermelho"])+"_ja_"+str(estado_jogo["pontuacao_jogador_azul"])+".txt","w") as documento:
                coor_b = doc_coordenadas_format(estado_jogo,"bola")
                coor_v = doc_coordenadas_format(estado_jogo,"jogador_vermelho")
                coor_a = doc_coordenadas_format(estado_jogo,"jogador_azul")
                documento.write(coor_b+"\n")
                documento.write(coor_v+"\n")
                documento.write(coor_a+"\n")
                documento.close()
            foi_golo("red")
            estado_jogo["var"]["bola"],estado_jogo["var"]["jogador_vermelho"],estado_jogo["var"]["jogador_azul"] = [],[],[]
            guarda_posicoes_para_var(estado_jogo)

def verifica_golo_jogador_azul(estado_jogo):
    '''
    Função responsável por verificar se um determinado jogador marcou golo. 
    Para fazer esta verificação poderá fazer uso das constantes: 
    LADO_MAIOR_AREA e 
    START_POS_BALIZAS. 
    Note que sempre que há um golo, deverá atualizar a pontuação do jogador, 
    criar um ficheiro que permita fazer a análise da jogada pelo VAR, 
    e reiniciar o jogo com a bola ao centro. 
    O ficheiro para o VAR deverá conter todas as informações necessárias 
    para repetir a jogada, usando as informações disponíveis no objeto 
    estado_jogo['var']. O ficheiro deverá ter o nome 
    
    replay_golo_jv_[TotalGolosJogadorVermelho]_ja_[TotalGolosJogadorAzul].txt 
    
    onde [TotalGolosJogadorVermelho], [TotalGolosJogadorAzul] 
    deverão ser substituídos pelo número de golos marcados pelo jogador vermelho 
    e azul, respectivamente. Este ficheiro deverá conter 3 linhas, estruturadas 
    da seguinte forma:
    Linha 1 - coordenadas da bola;
    Linha 2 - coordenadas do jogador vermelho;
    Linha 3 - coordenadas do jogador azul;
    
    Em cada linha, os valores de xx e yy das coordenadas são separados por uma 
    ',', e cada coordenada é separada por um ';'.
    '''
    b = estado_jogo["bola"]
    bola = b["objecto"]
    if b["pos_ant"][0]+b["xx"]-1.5<-LARGURA_JANELA/2+HITBOX_B: #-1.5 para evitar a margem de erro senão não é lido que se marca golo
        if -LADO_MAIOR_AREA/2<b["pos_ant"][1]<LADO_MAIOR_AREA/2:
            estado_jogo['pontuacao_jogador_azul'] += 1
            update_board(estado_jogo)
            bola.ht()
            estado_jogo["bola"] = None
            bola = criar_bola()
            bola["pos_ant"] = (5,5)            
            estado_jogo['bola'] = bola
            with open("replay_golo_jv_"+str(estado_jogo["pontuacao_jogador_vermelho"])+"_ja_"+str(estado_jogo["pontuacao_jogador_azul"])+".txt","w") as documento:
                coor_b = doc_coordenadas_format(estado_jogo,"bola")
                coor_v = doc_coordenadas_format(estado_jogo,"jogador_vermelho")
                coor_a = doc_coordenadas_format(estado_jogo,"jogador_azul")
                documento.write(coor_b+"\n")
                documento.write(coor_v+"\n")
                documento.write(coor_a+"\n")
                documento.close()
            foi_golo("blue")
            estado_jogo["var"]["bola"],estado_jogo["var"]["jogador_vermelho"],estado_jogo["var"]["jogador_azul"] = [],[],[]
            guarda_posicoes_para_var(estado_jogo)


def verifica_golos(estado_jogo):
    verifica_golo_jogador_vermelho(estado_jogo)
    verifica_golo_jogador_azul(estado_jogo)


def verifica_toque_jogador_azul(estado_jogo):
    '''
    Função responsável por verificar se o jogador tocou na bola. 
    Sempre que um jogador toca na bola, deverá mudar a direção desta.
    '''
    jogador = estado_jogo["jogador_azul"]
    b = estado_jogo["bola"]
    bola = b["objecto"]
    if jogador.distance(b["pos_ant"][0]+b["xx"],b["pos_ant"][1]+b["yy"]) < (HITBOX_B+HITBOX_J)*1.05:
        if b["xx"] > 0 and b["yy"] < 0 or b["xx"] < 0 and b["yy"] > 0:
            b["yy"] *= -1
        else:
            xx = b["xx"]
            yy = b["yy"]
            b["xx"] = -yy
            b["yy"] = -xx
        bola.pu()
        bola.goto(b["pos_ant"][0]+b["xx"],b["pos_ant"][1]+b["yy"])
        bola.pd()


def verifica_toque_jogador_vermelho(estado_jogo):
    '''
    Função responsável por verificar se o jogador tocou na bola. 
    Sempre que um jogador toca na bola, deverá mudar a direção desta.
    '''
    jogador = estado_jogo["jogador_vermelho"]
    b = estado_jogo["bola"]
    bola = b["objecto"]
    if jogador.distance(b["pos_ant"][0]+b["xx"],b["pos_ant"][1]+b["yy"]) < (HITBOX_B+HITBOX_J)*1.05:
        if b["xx"] < 0 and b["yy"] > 0 or b["xx"] > 0 and b["yy"] < 0:
            b["yy"] *= -1
        else:
            xx = b["xx"]
            yy = b["yy"]
            b["xx"] = -yy
            b["yy"] = -xx
        bola.pu()
        bola.goto(b["pos_ant"][0]+b["xx"],b["pos_ant"][1]+b["yy"])
        bola.pd()

def guarda_posicoes_para_var(estado_jogo):
    estado_jogo['var']['bola'].append(estado_jogo['bola']['objecto'].pos())
    estado_jogo['var']['jogador_vermelho'].append(estado_jogo['jogador_vermelho'].pos())
    estado_jogo['var']['jogador_azul'].append(estado_jogo['jogador_azul'].pos())

def criar_temporizador():
    tempo = t.Turtle()
    tempo.ht()
    tempo.color("black")
    vp(tempo,1,258)
    return tempo
    
def temporizador(inicio,tempo,muda):
    temporizador = time.time()-inicio
    minutos = "{:02.0f}".format(temporizador//60)
    segundos = "{:02.0f}".format(temporizador%60)
    if muda != segundos:
        tempo.clear()
        tempo.write(minutos+":"+segundos,False,"center",('Monaco',26))
    return segundos
    
def main():
    global inicio
    estado_jogo = init_state()
    setup(estado_jogo, True)
    estado_jogo.setdefault("inicio_jogo",inicio)
    tempo = criar_temporizador() #temporizador
    muda = -1
    while True:
        estado_jogo['janela'].update()
        muda = temporizador(inicio,tempo, muda) #temporizador
        if estado_jogo['bola'] is not None:
            movimenta_bola(estado_jogo)
        verifica_colisoes_ambiente(estado_jogo)
        verifica_golos(estado_jogo)
        if estado_jogo['jogador_vermelho'] is not None:
            verifica_toque_jogador_azul(estado_jogo)
        if estado_jogo['jogador_azul'] is not None:
            verifica_toque_jogador_vermelho(estado_jogo)

if __name__ == '__main__':
    main()
