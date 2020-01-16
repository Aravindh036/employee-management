#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct employee
{
    char name[10];
    int id;
    int age;
    char designation[10];
    float salary;
};

int main()
{
    struct employee emp[20];
    int CURR = 0;
    int id, temp_curr = 0, temp_data_int, record_found = 0;
    char temp_data_char[10], enter;
    while (1)
    {
        int ch;
        printf("\n\n1.Add an Employee\n2.Edit Employee Details\n3.Delete Employee Details\n4.View an Employee's Details\n5.Exit\n");
        scanf("%d", &ch);
        switch (ch)
        {
        case 1:
            printf("\nEmployee name: ");
            scanf("%s", emp[CURR].name);
            printf("\nEmployee ID: ");
            scanf("%d", &emp[CURR].id);
            printf("\nEmployee age: ");
            scanf("%d", &emp[CURR].age);
            printf("\nDesignation: ");
            scanf("%s", emp[CURR].designation);
            printf("\nSalary: ");
            scanf("%f", &emp[CURR].salary);
            CURR++;
            break;
        case 2:
            printf("\nEmployee ID: ");
            scanf("%d", &id);
            temp_curr = 0;
            for (int i = 0; i < 20; i++)
            {
                if (emp[temp_curr].id == id)
                {
                    record_found = 1;
                    printf("\nEdit Employee name [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%s", emp[temp_curr].name);
                    }
                    printf("\nEdit Employee ID [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%d", &emp[temp_curr].id);
                    }
                    printf("\nEdit Employee Age [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%d", &emp[temp_curr].age);
                    }
                    printf("\nEdit Employee Designation [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%s", emp[temp_curr].designation);
                    }
                    printf("\nEdit Employee Salary [y/n]: ");
                    scanf(" %c", &enter);
                    if (enter == 'y')
                    {
                        scanf("%f", &emp[temp_curr].salary);
                    }
                }
                temp_curr++;
            }
            if (record_found == 0)
            {
                printf("No record found for the ID");
            }
            break;
        case 3:
            printf("\nEmployee ID: ");
            scanf("%d", &id);
            temp_curr = 0;
            record_found=0;
            for (int i = 0; i < 20; i++)
            {
                if (emp[temp_curr].id == id)
                {
                    record_found=1;
                    emp[temp_curr].id = 0;
                }
                temp_curr++;
            }
            if(record_found==1){
                printf("Record deleted successfully");
            }
            else{
                printf("No record found for the ID");
            }
            break;
        case 4:
            printf("\nEmployee ID: ");
            scanf("%d", &id);
            temp_curr = 0;
            record_found = 0;
            for (int i = 0; i < 20; i++)
            {
                if (emp[temp_curr].id == id)
                {
                    record_found = 1;
                    printf("\nEmployee Name: %s", emp[temp_curr].name);
                    printf("\nEmployee ID: %d", emp[temp_curr].id);
                    printf("\nEmployee Age: %d", emp[temp_curr].age);
                    printf("\nEmployee Designation: %s", emp[temp_curr].designation);
                    printf("\nEmployee Salary: %0.2f", emp[temp_curr].salary);
                }
                temp_curr++;
            }
            if (record_found == 0)
            {
                printf("No record found for the ID");
            }
            break;
        default:
            exit(0);
        }
    }
}