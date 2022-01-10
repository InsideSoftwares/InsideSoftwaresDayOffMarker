#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json 

def createInsert(table_name, values):
    insert = {}
    insert['dbms'] = 'h2, mysql, postgres'
    insert['tableName'] = table_name
    columns = []
    for value in values:
        column = {}
        column['name'] = value['name']
        column['value'] = value['value']
        columns.append(
            {
                'column': column
            }
        )
    insert['columns'] = columns
    return insert

def saveFile(name,value):
    file = open(name, 'w')
    json.dump(value, file, indent = 2) 
    file.close()

def createFileCountries(countries):
    changeSet = {}
    changeSet['id'] = 'insert_countries'
    changeSet['author'] = 'System'

    changes = []

    for country in countries:
        if int(country['numeric_code']) == 76 or int(country['numeric_code']) == 840:
            values = []

            value = {}
            value['name'] = 'COUNTRY_ID'
            value['value'] = str(int(country['numeric_code']))
            values.append(value)

            value = {}
            value['name'] = 'NAME'
            value['value'] = country['name']
            values.append(value)
            
            value = {}
            value['name'] = 'ACRONYM'
            value['value'] = country['iso3']
            values.append(value)
            
            value = {}
            value['name'] = 'CODE'
            value['value'] = country['numeric_code']
            values.append(value)


            insert = {
                'insert': createInsert('DOM_COUNTRY', values)
            }
            changes.append(insert)

    changeSet['changes'] = changes
    saveFile('insert_countries.json',
        {
            'databaseChangeLog': [
                {
                    'changeSet': changeSet
                }
            ]
        }
    )

def createFileStates(countries):
    changeSet = {}
    changeSet['id'] = 'insert_states'
    changeSet['author'] = 'System'

    changes = []

    for country in countries:
        if int(country['numeric_code']) == 76 or int(country['numeric_code']) == 840:
            for state in country['states']:
                values = []

                value = {}
                value['name'] = 'STATE_ID'
                value['value'] = str(int(state['id']))
                values.append(value)

                value = {}
                value['name'] = 'NAME'
                value['value'] = state['name']
                values.append(value)
                
                value = {}
                value['name'] = 'ACRONYM'
                value['value'] = state['state_code']
                values.append(value)
                
                value = {}
                value['name'] = 'COUNTRY_ID'
                value['value'] = str(int(country['numeric_code']))
                values.append(value)


                insert = {
                    'insert': createInsert('DOM_STATE', values)
                }
                changes.append(insert)

    changeSet['changes'] = changes
    saveFile('insert_states.json',
        {
            'databaseChangeLog': [
                {
                    'changeSet': changeSet
                }
            ]
        }
    )

def createFileCities(countries):
    changeSet = {}
    changeSet['id'] = 'insert_states'
    changeSet['author'] = 'System'

    changes = []

    for country in countries:
        if int(country['numeric_code']) == 76 or int(country['numeric_code']) == 840:
            for state in country['states']:
                for city in state['cities']:
                    values = []

                    value = {}
                    value['name'] = 'CITY_ID'
                    value['value'] = str(int(city['id']))
                    values.append(value)

                    value = {}
                    value['name'] = 'NAME'
                    value['value'] = city['name']
                    values.append(value)
                    
                    value = {}
                    value['name'] = 'ACRONYM'
                    value['value'] = city['name'][:3]
                    values.append(value)
                    
                    value = {}
                    value['name'] = 'CODE'
                    value['value'] = str(int(city['id']))
                    values.append(value)

                    value = {}
                    value['name'] = 'STATE_ID'
                    value['value'] = str(int(state['id']))
                    values.append(value)

                    insert = {
                        'insert': createInsert('DOM_CITY', values)
                    }
                    changes.append(insert)

    changeSet['changes'] = changes
    saveFile('insert_cities.json',
        {
            'databaseChangeLog': [
                {
                    'changeSet': changeSet
                }
            ]
        }
    )

def openFile():
    name_file = 'countries_states_cities.json'
    # Opening JSON file
    f = open(name_file)
    
    # returns JSON object as
    # a dictionary
    countries = json.load(f)
    createFileCountries(countries)
    createFileStates(countries)
    createFileCities(countries)
    
    f.close()

openFile()