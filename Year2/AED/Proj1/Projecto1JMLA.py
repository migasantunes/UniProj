#Trabalho realizado por:
#José Miguel Luís Antunes, nº 2023211288
#Linguagem usada: Python

import numpy as np
import matplotlib.pyplot as plt
import time
import random
from scipy.optimize import curve_fit

def exaustivo1(lista):
    maximum = max(lista)
    minimum = min(lista)
    for n in range(minimum,maximum):
        if n not in lista: # no background "not in" tambem é um loop, ou seja ficam 2 loops, tal como pedido
            return n
    return "sopa"

def ordenamento(lista):
    lista.sort()
    for i in range(1,len(lista)):
        if lista[i] - lista[i-1] > 1:
            return lista[i-1]+1
    return lista[-1]+1

def elaborado(lista):
    minimum = min(lista)
    maximum = max(lista)

    sumList = sum(lista)
    expectedSum = (maximum*(maximum+1) - minimum*(minimum-1))/2

    return int(expectedSum - sumList)

def sequenceGenerator(listSize,listStart):
    sequence = list(range(listStart,listSize+listStart+1)) # usamos "+1" a contar ja com o elemento que damos pop
    random.shuffle(sequence)
    sequence.pop(random.randint(0,int(listSize)-1))
    return sequence

def valuesGenerator(size, algorithm):
    sum = 0
    for i in range(5):
        sequence = sequenceGenerator(size,1)
        timeIni = time.time()
        algorithm(sequence)
        timeEnd = time.time()
        sum += timeEnd - timeIni
    return sum/5

def quadratic_function(n, a, b):
    return a * n**2 + b

def nlogn_function(n, a, b):
    return a * n * np.log(n) + b

def main():
    ''' PARA A EXECUCAO MANUAL  
    listSize = int(input("What is the size of the sequence: "))
    listStart = int(input("In what value does it start: "))
    sequence = sequenceGenerator(listSize,listStart)

    #Solucao exaustiva
    timeIni = time.time()
    print(exaustivo1(sequence))
    timeEnd = time.time()
    print("exaustivo: {}".format(timeEnd - timeIni))

    #solucao por ordenamento
    timeIni = time.time()
    print(ordenamento(sequence))
    timeEnd = time.time()
    print("ordenamento: {}".format(timeEnd - timeIni))

    #solucao elaborada
    timeIni = time.time()
    print(elaborado(sequence))
    timeEnd = time.time()
    print("elaborado:  {}".format(timeEnd - timeIni))
    '''

    # tamanhos dos algoritmos
    sizesExaustivo = np.array([10000,25000,50000,75000,100000])
    sizesOrdenamentoElaborada = np.array([1000000, 2500000, 5000000, 7500000, 10000000])


    # Grafico solucao exaustiva O(n^2)/regressao quadratica
    averagesExaustivo = [valuesGenerator(size, exaustivo1) for size in sizesExaustivo]

    # Plot dos pontos
    plt.plot(sizesExaustivo, averagesExaustivo, "o", label="Exaustivo")
    
    # Plot da regressão
    params, _ = curve_fit(quadratic_function, sizesExaustivo, averagesExaustivo)
    a_fit, b_fit = params

    x_fit = np.linspace(min(sizesExaustivo), max(sizesExaustivo), 100)
    y_fit = quadratic_function(x_fit, a_fit, b_fit)

    plt.plot(x_fit, y_fit, "r--", label=f"Regressão: {a_fit:.6e} * n² + {b_fit:.6f}")

    #R^2
    residuals = averagesExaustivo - quadratic_function(sizesExaustivo, a_fit, b_fit)
    ss_res = np.sum(residuals**2)
    ss_tot = np.sum((averagesExaustivo - np.mean(averagesExaustivo))**2)
    r_squared = 1 - (ss_res / ss_tot)
    plt.text(0.05, 0.95, f"R^2: {r_squared:.6f}", transform=plt.gca().transAxes, verticalalignment='top', bbox=dict(facecolor='white', boxstyle='round'))

    plt.xlabel("Tamanho da sequencia")
    plt.ylabel("Tempo medio")
    plt.title("Exaustivo")

    plt.legend()
    plt.show()


    #Grafico solucao por ordenamento O(n log n)/regressao nlogn
    averagesOrdenamento = np.array([valuesGenerator(size, ordenamento) for size in sizesOrdenamentoElaborada])
    
    # Plot dos pontos
    plt.plot(sizesOrdenamentoElaborada,averagesOrdenamento, "o", label="Ordenamento")

    # Plot da regressão
    params, _  = curve_fit(nlogn_function, sizesOrdenamentoElaborada, averagesOrdenamento)
    a_fit, b_fit = params

    x_fit = np.linspace(min(sizesOrdenamentoElaborada), max(sizesOrdenamentoElaborada), 100)
    y_fit = nlogn_function(x_fit, a_fit, b_fit)

    plt.plot(x_fit, y_fit, "r--", label=f"Regressão: {a_fit:.6e} * n log(n) + {b_fit:.6f}")

    #R^2
    residuals = averagesOrdenamento - nlogn_function(sizesOrdenamentoElaborada, a_fit, b_fit)
    ss_res = np.sum(residuals**2)
    ss_tot = np.sum((averagesOrdenamento - np.mean(averagesOrdenamento))**2)
    r_squared = 1 - (ss_res / ss_tot)
    plt.text(0.05, 0.95, f"R^2: {r_squared:.6f}", transform=plt.gca().transAxes, verticalalignment='top', bbox=dict(facecolor='white', boxstyle='round'))

    plt.xlabel("Tamanho da sequencia")
    plt.ylabel("Tempo medio")
    plt.ylim(0,6)
    plt.title("Ordenamento")

    plt.legend()
    plt.show()


    #Grafico solucao elaborada O(n)/regressao linear
    averagesElaborado = np.array([valuesGenerator(size, elaborado) for size in sizesOrdenamentoElaborada])
    
    # Plot dos pontos
    plt.plot(sizesOrdenamentoElaborada, averagesElaborado, "o", label="Elaborado")

    # Plot da regressão
    x = sizesOrdenamentoElaborada
    y = averagesElaborado
    z = np.polyfit(x, y, 1)
    p = np.poly1d(z)
    plt.plot(x, p(x), "r--", label="Regressao Linear")
   
    #R^2
    residuals = y - p(x)
    ss_res = np.sum(residuals**2)
    ss_tot = np.sum((y - np.mean(y))**2)
    r_squared = 1 - (ss_res / ss_tot)
    plt.text(0.05, 0.95, f"R^2: {r_squared:.6f}", transform=plt.gca().transAxes, verticalalignment='top', bbox=dict(facecolor='white', boxstyle='round'))

    plt.xlabel("Tamanho da sequencia")
    plt.ylabel("Tempo medio")
    plt.ylim(0,6)
    plt.title("Elaborado")

    plt.legend()
    plt.show()


    #Tabela com os resultados
    tableData = [["Size",10000,25000,50000,75000,100000,1000000,2500000,5000000,7500000,10000000],
             ["Exaustivo"] + [f"{x:.4f}" for x in averagesExaustivo] + ["-"]*5,
             ["Ordenamento"] + ["-"]*5 + [f"{x:.4f}" for x in averagesOrdenamento],
             ["Elaborado"] + ["-"]*5 + [f"{x:.4f}" for x in averagesElaborado]]

    fig, ax = plt.subplots()
    table = ax.table(cellText=tableData, cellLoc='center', loc='center', colWidths=[0.1]*11)
    table.auto_set_font_size(False)
    table.set_fontsize(12)
    table.scale(1,1.5)
    ax.axis('off')
    plt.show()
                       

if __name__ == "__main__":
    main()
