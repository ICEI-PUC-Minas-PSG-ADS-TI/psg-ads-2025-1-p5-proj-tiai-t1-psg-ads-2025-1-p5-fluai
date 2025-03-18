"""Tabela Inicial

Revision ID: 569bb7db522b
Revises: 
Create Date: 2025-03-18 15:41:57.811276

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '569bb7db522b'
down_revision: Union[str, None] = None
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    op.create_table(
        'users',
        sa.Column('id', sa.Integer, primary_key=True, autoincrement=True),
        sa.Column('username', sa.String(100), nullable=False),
        sa.Column('password', sa.String(100), nullable=False),
        sa.Column('email', sa.String(100), unique=True, nullable=False),
        sa.Column('created_at', sa.DateTime, default=sa.func.now()),
        sa.Column('level', sa.String(100), default='A1'),
        sa.Column('progress_history', sa.String(100), default=''),
    )


def downgrade() -> None:
    op.drop_table('users')
