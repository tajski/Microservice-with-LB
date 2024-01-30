from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()
results_plus = []
results_minus = []
results_multiply = []
results_divide = []


class Calculation(BaseModel):
    number_one: float
    number_two: float


@app.post('/plus')
async def calculate_plus(calculation: Calculation):
    result = calculation.number_one + calculation.number_two
    results_plus.append(result)
    return result


@app.post('/minus')
async def calculate_minus(calculation: Calculation):
    result = calculation.number_one - calculation.number_two
    results_minus.append(result)
    return result


@app.post('/multiply')
async def calculate_multiply(calculation: Calculation):
    result = calculation.number_one * calculation.number_two
    results_multiply.append(result)
    return result


@app.post('/divide')
async def calculate_divide(calculation: Calculation):
    result = calculation.number_one / calculation.number_two
    results_divide.append(result)
    return result

