# Curso: Estratégias para gerenciar massa de dados em testes + DbUnit
**Testes usando Java, JUnit, DbUnit**
 
## Seleção de estratégia (1-5):
 
### 1)O teste é Manual?
>
>a) SIM
>>**2) O teste é Exploratório?**
>>>		a) SIM -> Usar a Estratégia 1
>>>		b) NÃO -> Verificar "Controle dos dados"
>	
>b) NÃO
>>		Verificar "Controle dos dados"
		
### 3) Temos "Controle dos dados"?
>
>a) SIM
>>**4)O Banco de Dados é instável?**
>>>		a) SIM (Usar estratégia 2 ou 3)
>>>			5) O teste é simples ou específico?
>>>				SIM -> Usar a Estratégia 2
>>>			6) O teste preve reuso de massas?
>>>				SIM -> Usar a Estratégia 3

>>>		b) NÃO (Usar estratégia 4 ou 5)
>>>			7) Os testes são antisociais ou tem cenários muito complexos?
>>>				a) SIM -> Usar a Estratégia 4
>>>				b) NÃO -> Usar a Estratégia 5
	
>b) NÃO
>>**9) O teste é simples ou específico?**
>>>		a) SIM -> Usar a Estratégia 2

>>**10) O teste preve reuso manual?**
>>>		b) SIM -> Usar a Estratégia 3

By: Aldrovando Camargo Neves - 2021