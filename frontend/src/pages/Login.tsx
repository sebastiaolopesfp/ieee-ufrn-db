import { useNavigate, Link } from 'react-router-dom';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { toast } from 'sonner';

import { authService } from '@/api/auth.api';
import { useAuth } from '@/contexts/AuthContext';

import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Checkbox } from '@/components/ui/checkbox';
import { Card, CardContent } from '@/components/ui/card';
import {
    Field,
    FieldDescription,
    FieldError,
    FieldGroup,
    FieldLabel,
} from '@/components/ui/field';

import logoAzul from '../assets/logo_azul.png';

// 1. Definição do Schema de validação com Zod
const loginSchema = z.object({
    emailPessoal: z.email('E-mail inválido').min(1, 'O e-mail é obrigatório'),
    senha: z.string().min(1, 'A senha é obrigatória'),
    manterConectado: z.boolean(),
});

type LoginFormValues = z.infer<typeof loginSchema>;

export function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();

    // 2. Initialize React Hook Form
    const form = useForm<LoginFormValues>({
        resolver: zodResolver(loginSchema),
        defaultValues: {
            emailPessoal: '',
            senha: '',
            manterConectado: false,
        },
    });

    // 3. Submit Handler
    async function onSubmit(data: LoginFormValues) {
        try {
            const response = await authService.login(data);
            login(response.token);
            navigate('/');
        } catch (err) {
            console.error(err);
            toast.error('Credenciais inválidas. Verifique seu e-mail e senha.');
        }
    }

    return (
        <div className="flex min-h-screen flex-col items-center justify-center bg-gray-100 font-sans p-6">
            <Card className="w-full max-w-md p-8 rounded-sm border-gray-200 shadow-sm">
                <CardContent className="p-0 space-y-8">
                    {/* Header */}
                    <div className="text-center flex flex-col items-center">
                        <img
                            src={logoAzul}
                            alt="Logo"
                            className="h-10 w-auto mb-4"
                        />
                        <h1 className="text-2xl font-bold tracking-tight text-gray-900">
                            Acesso ao Sistema
                        </h1>
                        <p className="text-gray-500 text-sm font-medium">
                            CoordIEEEna
                        </p>
                    </div>

                    {/* 4. Native HTML form wrapper */}
                    <form
                        id="login-form"
                        onSubmit={form.handleSubmit(onSubmit)}
                        className="space-y-4"
                    >
                        <FieldGroup>
                            {/* Email Field */}
                            <Controller
                                name="emailPessoal"
                                control={form.control}
                                render={({ field, fieldState }) => (
                                    <Field data-invalid={fieldState.invalid}>
                                        <FieldLabel htmlFor="login-email">
                                            E-mail
                                        </FieldLabel>
                                        <Input
                                            {...field}
                                            id="login-email"
                                            type="email"
                                            placeholder="seu.email@ieee.org"
                                            aria-invalid={fieldState.invalid}
                                            autoComplete="email"
                                        />
                                        {fieldState.invalid && (
                                            <FieldError
                                                errors={[fieldState.error]}
                                            />
                                        )}
                                    </Field>
                                )}
                            />

                            {/* Password Field */}
                            <Controller
                                name="senha"
                                control={form.control}
                                render={({ field, fieldState }) => (
                                    <Field data-invalid={fieldState.invalid}>
                                        <FieldLabel htmlFor="login-senha">
                                            Senha
                                        </FieldLabel>
                                        <Input
                                            {...field}
                                            id="login-senha"
                                            type="password"
                                            placeholder="•••••••••"
                                            aria-invalid={fieldState.invalid}
                                            autoComplete="current-password"
                                        />
                                        {fieldState.invalid && (
                                            <FieldError
                                                errors={[fieldState.error]}
                                            />
                                        )}
                                    </Field>
                                )}
                            />

                            <div className="flex items-center justify-between text-sm">
                                <Controller
                                    name="manterConectado"
                                    control={form.control}
                                    render={({ field, fieldState }) => (
                                        <Field
                                            orientation="horizontal"
                                            data-invalid={fieldState.invalid}
                                            className="w-fit"
                                        >
                                            <Checkbox
                                                id="login-lembrar"
                                                checked={field.value}
                                                onCheckedChange={field.onChange}
                                                aria-invalid={
                                                    fieldState.invalid
                                                }
                                                className="cursor-pointer"
                                            />
                                            <FieldLabel
                                                htmlFor="login-lembrar"
                                                className="font-normal cursor-pointer text-gray-600"
                                            >
                                                Lembrar de mim
                                            </FieldLabel>
                                        </Field>
                                    )}
                                />

                                <Link
                                    to="/"
                                    className="text-primary hover:underline font-medium"
                                >
                                    Esqueceu a senha?
                                </Link>
                            </div>

                            <Button
                                type="submit"
                                form="login-form"
                                className="w-full cursor-pointer"
                                size="lg"
                                disabled={form.formState.isSubmitting}
                            >
                                {form.formState.isSubmitting
                                    ? 'Entrando...'
                                    : 'Entrar no Sistema'}
                            </Button>
                        </FieldGroup>
                    </form>

                    <div className="text-center text-sm text-gray-800">
                        Ainda não tem uma conta?{' '}
                        <Link
                            to="/"
                            className="text-primary font-semibold hover:underline"
                        >
                            Cadastre-se
                        </Link>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
}
